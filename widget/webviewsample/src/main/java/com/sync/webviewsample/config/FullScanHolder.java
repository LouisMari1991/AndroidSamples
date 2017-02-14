package com.sync.webviewsample.config;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by YH on 2017-02-14.
 */

public class FullScanHolder extends FrameLayout {
  public FullScanHolder(Context context) {
    super(context);
  }

  public FullScanHolder(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FullScanHolder(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    return true;
  }
}
