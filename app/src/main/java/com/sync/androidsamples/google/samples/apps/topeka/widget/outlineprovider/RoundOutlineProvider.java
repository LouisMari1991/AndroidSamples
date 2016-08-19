package com.sync.androidsamples.google.samples.apps.topeka.widget.outlineprovider;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 *
 * Creates round outline for views
 *
 * Created by Administrator on 2016/8/18 0018.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP) public class RoundOutlineProvider extends ViewOutlineProvider{

  private final int mSize;

  public RoundOutlineProvider(int size) {
    if (0 > size) {
      throw new IllegalArgumentException("size needs to be > 0. Actually was " + size);
    }
    mSize = size;
  }

  @Override public void getOutline(View view, Outline outline) {
    outline.setOval(0,0,mSize,mSize);
  }
}
