package com.sync.androidsamples.google.samples.apps.topeka.helper;

import android.os.Parcel;

/**
 * Author：Administrator on 2016/8/20 0020 17:39
 * Contact：289168296@qq.com
 */
public class ParcelableHelper {

  public static boolean readBoolean(Parcel in) {
    return 0 == in.readInt();
  }

}
