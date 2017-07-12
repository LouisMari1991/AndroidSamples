package com.googlesamples.topeka.widget;

import android.app.SharedElementCallback;
import android.os.Build;

/**
 * Description:
 * Author：SYNC on 2017-07-12 23:33
 * Contact：289168296@qq.com
 */
@android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) public class TextSharedElementCallback
    extends SharedElementCallback {

  private final int   mInitialPaddingStart;
  private final float mInitialTextSize;
  private       float mTargetViewPaddingStart;
  private static final String TAG = "TextResize";

  public TextSharedElementCallback(int initialPaddingStart, float initialTextSize) {
    mInitialPaddingStart = initialPaddingStart;
    mInitialTextSize = initialTextSize;
  }


}
