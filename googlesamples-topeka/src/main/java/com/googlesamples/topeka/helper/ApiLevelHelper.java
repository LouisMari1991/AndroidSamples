package com.googlesamples.topeka.helper;

import android.os.Build;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class ApiLevelHelper {

  private ApiLevelHelper() {
    // no instance
  }

  /**
   * Checks if the current api level is at least than the provided value.
   */
  public static boolean isAtLeast(int apiLevel) {
    return Build.VERSION.SDK_INT >= apiLevel;
  }

  /**
   * Checks if the current api level is at lower than the provided value.
   */
  public static boolean isLowerThan(int apiLevel){
    return Build.VERSION.SDK_INT < apiLevel;
  }


}
