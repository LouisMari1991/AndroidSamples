package com.sync.webviewsample.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author：Administrator on 2017/2/15 0015 23:12
 * Contact：289168296@qq.com
 */
public class CheckNetwork {

  /**
   * 判断网络是否连通
   */
  public static boolean isNetworkConnected(Context context) {
    try {
      if (context != null) {
        @SuppressWarnings("static-access") ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
      } else {
        /**如果context为空，就返回false，表示网络未连接*/
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isWifiConnected(Context context) {
    if (context != null) {
      ConnectivityManager cm =
          (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
      NetworkInfo info = cm.getActiveNetworkInfo();
      return info != null && (info.getType() == ConnectivityManager.TYPE_WIFI);
    } else {
      /**如果context为null就表示为未连接*/
      return false;
    }
  }
}
