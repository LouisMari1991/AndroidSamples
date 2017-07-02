package com.googlesamples.topeka.helper;

import android.support.v4.view.ViewCompat;
import android.widget.TextView;

/**
 * Author：Administrator on 2016/8/20 0020 17:39
 * Contact：289168296@qq.com
 */
public class ViewUtils {

  private ViewUtils() {
    // no instance
  }

  public static void setPaddingStart(TextView target, int paddingStart) {
    ViewCompat.setPaddingRelative(target, paddingStart, target.getPaddingTop(), ViewCompat.getPaddingEnd(target),
        target.getPaddingBottom());
  }
}
