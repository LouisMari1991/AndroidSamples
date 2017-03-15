package com.sync.finistwo.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by YH on 2017-03-15.
 */

public class MeasureUtil {

  /**
   * 获取屏幕尺寸
   *
   * @param activity Activity
   * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
   */
  public static int[] getScreenSize(Activity activity) {
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    return new int[] { metrics.widthPixels, metrics.heightPixels };
  }

}
