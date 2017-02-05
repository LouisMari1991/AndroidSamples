package com.sync.topactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Author：Administrator on 2017/2/5 0005 10:10
 * Contact：289168296@qq.com
 */
public class SPHelper {

  public static boolean isShowWindow(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean("is_show_window", true);
  }

  public static void setIsShowWindow(Context context, boolean isShow) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.edit().putBoolean("is_show_window", isShow).commit();
  }
}
