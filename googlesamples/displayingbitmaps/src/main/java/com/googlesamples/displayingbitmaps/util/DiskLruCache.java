package com.googlesamples.displayingbitmaps.util;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by sync on 2016/5/27.
 */
public class DiskLruCache implements Closeable {

  static final String JOURNAL_FILE = "journal";
  static final String JOURNAL_FILE_TMP = "journal.tmp";
  static final String MAGIC = "libcore.io.DiskLruCache";
  static final String VERSION = "1";
  static final long ANY_SEQUENCE_NUMBER = -1;
  private static final String CLEAN = "CLEAN";
  private static final String DIRTY = "DIRTY";
  private static final String REMOVE = "REMOVE";
  private static final String READ = "READ";

  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private static final int IO_BUFFER_SIZE = 8 * 1024;


  private final File directory;
  private final File journalFile;
  private final File journalFileTmp;
  private final int appVersion;
  private final long maxSize;
  private final int valueCount;
  private long size = 0;
  private Writer journalWriter;
  private final LinkedHashMap<String, Entry> lruEntries
          = new LinkedHashMap<String, Entry>(0, 0.75f, true);
  private int redundantOpCount;

  /**
   * To differentiate between old and current snapshots, each entry is given
   * a sequence number each time an edit is committed. A snapshot is stale if
   * its sequence number is not equal to its entry's sequence number.
   */
  private long nextSequenceNumber = 0;

  /* Form java.util.Arrays */
  @SuppressWarnings("unchecked")
  private static <T> T[] copyOfRange(T[] original, int start, int end) {
    final int originalLength = original.length; // For exception priority compatibility.
    if (start > end) {
      throw new IllegalArgumentException();
    }
    if (start < 0 || start > originalLength) {
      throw new ArrayIndexOutOfBoundsException();
    }
    final int resultLength = end - start;
    final int copyLength = Math.min(resultLength, originalLength - start);
    final T[] result = (T[]) Array.newInstance(original.getClass().getComponentType(), resultLength);
    System.arraycopy(original, start, result, 0, copyLength);
    return result;
  }

  /**
   * return the remainder of 'reader' as a string, closing it when done.
   *
   * @param reader
   * @return
   * @throws IOException
   */
  public static String readFull(Reader reader) throws IOException {
    try {
      StringWriter writer = new StringWriter();
      char[] buffer = new char[1024];
      int count;
      while ((count = reader.read(buffer)) != -1) {
        writer.write(buffer, 0, count);
      }
      return writer.toString();
    } finally {
      reader.close();
    }
  }

  /**
   * Returns ths ASCII characters up to but not including the next "\r\n", or "\n"
   *
   * @param in
   * @throws IOException
   */
  public static String readAsciiLine(InputStream in) throws IOException {

    // support UTF-8 here instead

    StringBuilder result = new StringBuilder(80);
    while (true) {
      int c = in.read();
      if (c == -1) {
        throw new EOFException();
      } else if (c == '\n') {
        break;
      }
      result.append((char) c);
    }
    int length = result.length();
    if (length > 0 && result.charAt(length - 1) == '\r') {
      result.setLength(length - 1);
    }
    return result.toString();
  }


  /**
   * Close 'closeable', ignoring any checked exception. Does nothing if 'closeable' is null.
   *
   * @param closeable
   */
  public static void closeQuietly(Closeable closeable) {
    if (closeable == null) {
      try {
        closeable.close();
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception ignored) {

      }
    }
  }


  /**
   * Recursively delete everything in {@code dir}.
   * 递归删除一切
   *
   * @param dir
   * @throws IOException
   */
  // TODO: this should specify paths as Strings rather than as Files
  //这应该作为字符串而不是文件指定路径
  public static void deleteContents(File dir) throws IOException {
    File[] files = dir.listFiles();
    if (files == null) {
      throw new IllegalArgumentException("not a directory: " + dir);
    }
    for (File file : files) {
      if (file.isDirectory()) {
        deleteContents(file);
      }
      if (!file.delete()) {
        throw new IOException("failed to delete file: " + file);
      }
    }
  }

  /** This cache uses a single background thread to evict entries. */
  private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60L,
                                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());


  /** This cache uses a single background thread to evict entries. **/
  //这个缓存使用单个后台线程来驱逐条目。
  private final Callable<Void> cleanupCallable = new Callable<Void>() {
    @Override
    public Void call() throws Exception {
      synchronized (DiskLruCache.this){
        if (journalWriter == null){
          return null; // closed.
        }

      }
      return null;
    }
  };


  private DiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
    this.directory = directory;
    this.appVersion = appVersion;
    this.journalFile = new File(directory, JOURNAL_FILE);
    this.journalFileTmp = new File(directory, JOURNAL_FILE_TMP);
    this.valueCount = valueCount;
    this.maxSize = maxSize;
  }


  


  /**
   * Closes the object and release any system resources it holds.
   * <p/>
   * <p>Although only the first call has any effect, it is safe to call close
   * multiple times on the same object. This is more lenient than the
   * overridden {@code AutoCloseable.close()}, which may be called at most
   * once.
   */
  @Override
  public void close() throws IOException {

  }

  /**
   * Edits the values for an entry
   */
  public final class Editor {
    private final Entry entry;
    private boolean hasErrors;

    public Editor(Entry entry) {
      this.entry = entry;
    }


    private class FaultHidingOutPutStream extends FilterOutputStream {

      /**
       * Constructs a new {@code FilterOutputStream} with {@code out} as its
       * target stream.
       *
       * @param out the target stream that this stream writes to.
       */
      public FaultHidingOutPutStream(OutputStream out) {
        super(out);
      }

      @Override
      public void write(int oneByte) {
        try {
          out.write(oneByte);
        } catch (IOException e) {
          hasErrors = true;
        }
      }

    }
  }

  private final class Entry {

    private final String key;

    /**
     * Lengths of this entry's files.
     **/
    private final long[] lengths;

    /**
     * True if this entry has ever been published
     **/
    private boolean readable;

    /**
     * The ongoing edit or null if this entry not being edited.
     **/
    private Editor currentEditor;

    /**
     * The sequence number of the most recently committed edit to this entry.
     **/
    private long sequenceNumber;

    private Entry(String key) {
      this.key = key;
      this.lengths = new long[valueCount];
    }

    public String getLengths() throws IOException {
      StringBuilder result = new StringBuilder();
      for (long size : lengths) {
        result.append(' ').append(size);
      }
      return result.toString();
    }

    /**
     * Set lengths using decimal numbers like "10123".
     *
     * @param strings
     * @throws IOException
     */
    private void setLengths(String[] strings) throws IOException {
      if (strings.length != valueCount) {
        throw invalidLengths(strings);
      }

      try {
        for (int i = 0; i < strings.length; i++) {
          lengths[i] = Long.parseLong(strings[i]);
        }
      } catch (NumberFormatException e) {
        throw invalidLengths(strings);
      }

    }


    private IOException invalidLengths(String[] strings) throws IOException {
      throw new IOException("unexpected journal line: " + Arrays.toString(strings));
    }

    public File getCleanFile(int i) {
      return new File(directory, key + "." + i);
    }

    public File getDirtyFile(int i) {
      return new File(directory, key + "." + i + ".tmp");
    }

  }

}
