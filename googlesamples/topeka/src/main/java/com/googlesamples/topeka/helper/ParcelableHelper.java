package com.googlesamples.topeka.helper;

import android.os.Parcel;

/**
 * Author：Administrator on 2016/8/20 0020 17:39
 * Contact：289168296@qq.com
 */
public class ParcelableHelper {

  private ParcelableHelper() {
    // no instance
  }

  /**
   * Write a single boolean to a {@link Parcel}
   *
   * @param dest Destination of the value.
   * @param toWrite Value to Write.
   * @see ParcelableHelper#readBoolean(Parcel)
   *
   * 2016/8/30 0030 22:31
   */
  public static void writeBoolean(Parcel dest, boolean toWrite) {
    dest.writeInt(toWrite ? 0 : 1);
  }

  /**
   * Retrieves a single boolean from a Parcle.
   *
   * @param in The source containing the stored boolean.
   * @see ParcelableHelper#writeBoolean(Parcel, boolean)
   *
   * 2016/8/30 0030 22:33
   */
  public static boolean readBoolean(Parcel in) {
    return 0 == in.readInt();
  }

  /**
   * Allows memory efficient percolation of enums.
   *
   * @param dest Destination of the value.
   * @param e Value to write.
   *
   * 2016/8/30 0030 22:39
   */
  public static void writeEnumValue(Parcel dest, Enum e) {
    dest.writeInt(e.ordinal());
  }

}
