package com.sync.customviewstudy.view;

import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Description: Creates round outlines for views.
 * Author：Mari on 2017-07-25 22:30
 * Contact：289168296@qq.com
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) public class RoundOutlineProvider extends ViewOutlineProvider {

  private final int mSize;

  public RoundOutlineProvider(int size) {
    mSize = size;
  }

  @Override public void getOutline(View view, Outline outline) {
    outline.setOval(0, 0, mSize, mSize);
  }
}
