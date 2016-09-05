package com.googlesamples.displayingbitmaps.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.LruCache;
import com.googlesamples.displayingbitmaps.BuildConfig;
import java.io.File;
import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sync on 2016/5/22.
 */
public class ImageCache {

  private static final String TAG = "ImageCache";

  // Default memory cache size in kilobytes
  private static final int DEFAULT_MEN_CACHE_SIZE = 1024 * 5; //5MB

  // Default disk cache size in byte
  private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 10;//10MB

  // Compression settings when writing images to disk cache
  private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
  private static final int DEFAULT_COMPRESS_QUALITY = 70;
  private static final int DISK_CACHE_INDEX = 0;

  // Constants to easily toggle various caches
  private static final boolean DEFAULT_MEN_CACHE_ENABLE = true;
  private static final boolean DEFAULT_DISK_CACHE_ENABLE = true;
  private static final boolean DEFAULT_INIT_DISK_CACHE_ON_CREATE = false;

  private DiskLruCache mDiskLruCache;
  private LruCache<String, BitmapDrawable> mMemoryCache;
  private ImageCacheParams mCacheParams;
  private final Object mDiskCacheLock = new Object();

  private Set<SoftReference<Bitmap>> mReusableBitmaps;


  /**
   * Create a new ImageCache object using the specified parameters. This should not be
   * called directly by other classes, instead use
   *
   * @param cacheParams
   */
  private ImageCache(ImageCacheParams cacheParams) {
    init(cacheParams);
  }

  public static ImageCache getInstance(
          FragmentManager fragmentManager, ImageCacheParams cacheParams) {

    final RetainFragment mRetainFragment = findOrCreateRetainFragment(fragmentManager);

    // See if we already have an ImageCache stored in RetainFragment
    ImageCache imageCache = (ImageCache) mRetainFragment.getObject();

    // No existing ImageCache, create one and store it in RetainFragment
    if (imageCache == null) {
      imageCache = new ImageCache(cacheParams);
      mRetainFragment.setObject(imageCache);
    }

    return imageCache;
  }


  /**
   * Initialize the cache, providing all parameters.
   *
   * @param cacheParams The cache parameters to initialize the cache
   */
  private void init(ImageCacheParams cacheParams) {
    mCacheParams = cacheParams;

    if (cacheParams.memoryCacheEnable) {
      if (BuildConfig.DEBUG) {
        Log.d(TAG, "Memory cache create (size = " + mCacheParams.memCacheSize + ")");
      }


      if (Utils.hasHoneycomb()) {
        mReusableBitmaps = Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
      }

      mMemoryCache = new LruCache<String, BitmapDrawable>(mCacheParams.memCacheSize) {
        /**
         * Notify the removed entry is not longer being cached
         * 通知缓存删除条目
         *
         * Called for entries that have been evicted or removed. This method is
         * invoked when a value is evicted to make space, removed by a call to
         * {@link #remove}, or replaced by a call to {@link #put}. The default
         * implementation does nothing.
         * <p/>
         * <p>The method is called without synchronization: other threads may
         * access the cache while this method is executing.
         *
         * @param evicted  true if the entry is being removed to make space, false
         *                 if the removal was caused by a {@link #put} or {@link #remove}.
         * @param key
         * @param oldValue
         * @param newValue the new value for {@code key}, if it exists. If non-null,
         *                 this removal was caused by a {@link #put}. Otherwise it was caused by
         *                 an eviction or a {@link #remove}.
         */
        @Override
        protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue, BitmapDrawable newValue) {
          super.entryRemoved(evicted, key, oldValue, newValue);
          if (RecyclingBitmapDrawable.class.isInstance(oldValue)) {
            // The removed entry is a recycling drawable, so notify it
            // that it has been removed form the memory cache
            ((RecyclingBitmapDrawable) oldValue).setIsCached(false);
          } else {
            //The removed entry is a standard BitmapDrawable

            if (Utils.hasHoneycomb()) {
              // We're running on Honey on Honeycomb ot later, so add the bitmap
              // to a SoftReference set for possible use with inBitmap later
              mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue.getBitmap()));
            }
          }
        }

        /**
         *  Measure item size in kilobytes rather rhan units which is more practical
         *  for a bitmap cache
         *
         *  测量项目大小字节,而rhan单位哪个更实用
         * 位图缓存
         *
         * Returns the size of the entry for {@code key} and {@code value} in
         * user-defined units.  The default implementation returns 1 so that size
         * is the number of entries and max size is the maximum number of entries.
         * <p/>
         * <p>An entry's size must not change while it is in the cache.
         *
         * @param key
         * @param value
         */
        @Override
        protected int sizeOf(String key, BitmapDrawable value) {
          final int bitmapSize = getBitmapSize(value) / 1024;
          return bitmapSize == 0 ? 1 : bitmapSize;
        }

      };

      if (cacheParams.initDiskCacheOnCreate){
//        initD
      }

    }
  }

  public void initDiskCache(){
    synchronized (mDiskCacheLock){
//      if (mDiskLruCache == null || mDiskLruCache.is)
    }
  }

  public static class ImageCacheParams {
    public int memCacheSize = DEFAULT_MEN_CACHE_SIZE;
    public int diskCacheSize = DEFAULT_DISK_CACHE_SIZE;
    public File diskCacheDir;
    public Bitmap.CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
    public int compressQuality = DEFAULT_COMPRESS_QUALITY;
    public boolean memoryCacheEnable = DEFAULT_MEN_CACHE_ENABLE;
    public boolean diskCacheEnable = DEFAULT_DISK_CACHE_ENABLE;
    public boolean initDiskCacheOnCreate = DEFAULT_INIT_DISK_CACHE_ON_CREATE;

    /**
     * Create a set of image cache parameters that can be provider to
     *
     * @param context                A context to use.
     * @param diskCacheDirectoryName A unique subdirectory name that name that will be appended to the
     *                               application cache directory. Usually "cache" of "images"
     *                               is sufficient
     */
    public ImageCacheParams(Context context, String diskCacheDirectoryName) {
      diskCacheDir = getDiskCacheDir(context, diskCacheDirectoryName);
    }


    /**
     * @param percent Percent of available app memory to use to size memory size
     */
    public void setMemCacheSizePercent(float percent) {
      if (percent < 0.01f || percent > 0.8f) {
        throw new IllegalArgumentException("setMemCacheSizePercent - percent must be "
                + "between 0.01 and 0.8 (inclusive)");
      }
    }
  }

  /**
   * @param candidate     - Bitmap to check
   * @param targetOptions - Options that have the out* value populated
   * @return true if <code>candidate<code/> can be used for inBitmap re-use with
   * <code>targetOptions</code>
   */
  @TargetApi(Build.VERSION_CODES.KITKAT)
  public static boolean canUseForInBitmap(
          Bitmap candidate, BitmapFactory.Options targetOptions) {
    if (!Utils.hasKitKat()) {
      //BEGIN_INCLUDE(can_use_for_inbitmap)
      return candidate.getWidth() == targetOptions.outWidth
              && candidate.getHeight() == targetOptions.outHeight
              && targetOptions.inSampleSize == 1;
    }
    //From Android 4.4 (KitKat) onward we can re-use if the byte size of the new bitmap
    //is smaller than the reusable bitmap candidate allocation byte count.
    int width = targetOptions.outWidth / targetOptions.inSampleSize;
    int height = targetOptions.outHeight / targetOptions.inSampleSize;
    int byteCount = width * height * getBytesPerpixel(candidate.getConfig());
    return byteCount <= candidate.getAllocationByteCount();
    //END_INCLUDE(can_use_for_inbitmap)
  }

  private static int getBytesPerpixel(Bitmap.Config config) {
    if (config == Bitmap.Config.ARGB_8888) {
      return 4;
    } else if (config == Bitmap.Config.RGB_565) {
      return 2;
    } else if (config == Bitmap.Config.ARGB_4444) {
      return 2;
    } else if (config == Bitmap.Config.ALPHA_8) {
      return 1;
    }
    return 1;
  }


  public static File getDiskCacheDir(Context context, String uniqueName) {
    final String
        cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
            !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath()
            : context.getCacheDir().getPath();

    return new File(cachePath + File.separator + uniqueName);
  }

  /**
   * A hashing method changes a string (like a URL) into a hash suitable for using as a disk filename
   *
   * @param key
   * @return
   */
  public static String hashKeyForDisk(String key) {
    String cacheKey;
    try {
      final MessageDigest mdigest = MessageDigest.getInstance("MD5");
      mdigest.update(key.getBytes());
      cacheKey = bytesToHexString(mdigest.digest());
    } catch (NoSuchAlgorithmException e) {
      cacheKey = String.valueOf(key.hashCode());
      e.printStackTrace();
    }
    return cacheKey;
  }

  /**
   * A hashing method that changes a string (like a URL) into hash suitable using as a
   * disk filename
   *
   * @param bytes
   * @return
   */
  private static String bytesToHexString(byte[] bytes) {
    // http://stackoverflow.com/questions/332079
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(0xFF & bytes[i]);
      if (hex.length() == 1) {
        sb.append('0');
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  /**
   * Get the size in bytes a bitmap in a BitmapDrawable. Note thar form
   *
   * @param value
   * @return size in bytes
   */
  public static int getBitmapSize(BitmapDrawable value) {
    Bitmap bitmap = value.getBitmap();

    if (Utils.hasKitKat()) {
      return bitmap.getAllocationByteCount();
    }

    if (Utils.hasHoneycombMR1()) {
      return bitmap.getByteCount();
    }

    //pre HC-MR1
    return bitmap.getRowBytes() * bitmap.getHeight();
  }

  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
  public static boolean isExternalStorageRemovable() {
    if (Utils.hasFroyo()) {
      return Environment.isExternalStorageRemovable();
    }
    return true;
  }

  @TargetApi(Build.VERSION_CODES.FROYO)
  public static File getExternalCacheDir(Context context) {
    if (Utils.hasFroyo()) {
      return context.getExternalCacheDir();
    }

    //Before Froyo we need to construct the external cache dir ourselves
    final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
    return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
  }

  /**
   * Check how much usable space is available at a given path.
   */
  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
  public static long getUsableSpace(File path) {
    if (Utils.hasGingerbread()) {
      return path.getUsableSpace();
    }
    final StatFs stats = new StatFs(path.getPath());
    return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
  }

  /**
   * Locate an existing instance of this Fragment or if not found, create and
   * add it using FragmentManager.
   *
   * @param fm The FragmentManager manager to use.
   * @return The existing fragment of the Fragment or new instance if just
   * create
   */
  private static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
    RetainFragment mRetainFragment = (RetainFragment) fm.findFragmentByTag(TAG);
    if (mRetainFragment == null) {
      mRetainFragment = new RetainFragment();
      fm.beginTransaction().add(mRetainFragment, TAG).commitAllowingStateLoss();
    }

    return mRetainFragment;
  }

  public static class RetainFragment extends Fragment {

    private Object mObject;

    public RetainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Make sure this Fragment is retained over a configuration change
      setRetainInstance(true);
    }

    /**
     * Store a single object in this Fragment
     *
     * @return Object The object to store
     */
    public Object getObject() {
      return mObject;
    }

    /**
     * Get the stored object
     *
     * @param object The stored object
     */
    public void setObject(Object object) {
      mObject = object;
    }
  }
}




















