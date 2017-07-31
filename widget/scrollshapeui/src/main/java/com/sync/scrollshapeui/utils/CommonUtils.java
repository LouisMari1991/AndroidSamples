package com.sync.scrollshapeui.utils;

import android.content.res.Resources;
import com.sync.scrollshapeui.app.MApp;

/**
 * Description:
 * Author：Mari on 2017-07-31 22:40
 * Contact：289168296@qq.com
 */
public class CommonUtils {

  private static Resources getResoure() {
    return MApp.get().getResources();
  }

  public static String getString(int resid) {
    return getResoure().getString(resid);
  }

  public static float getDimens(int resId) {
    return getResoure().getDimension(resId);
  }

}
