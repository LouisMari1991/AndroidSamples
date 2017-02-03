package com.sync.viewbase.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created by YH on 2017-02-03.
 */

public class TestButton extends TextView {

  private static final String TAG = "TestButton";
  private int mScaledTouchSlop;
  private int mLastX;
  private int mLastY;

  public TestButton(Context context) {
    super(context);
    init();
  }

  public TestButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TestButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void init() {
    mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    Log.i(TAG, "sts:" + mScaledTouchSlop);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    int x = (int) event.getRawX();
    int y = (int) event.getRawY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:

        break;
      case MotionEvent.ACTION_MOVE:
        int deltaX = x - mLastX;
        int deltaY = y - mLastY;
        Log.i(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
        int translationX = (int) (ViewCompat.getTranslationX(this) + deltaX);
        int translationY = (int) (ViewCompat.getTranslationY(this) + deltaY);
        ViewCompat.setTranslationX(this, translationX);
        ViewCompat.setTranslationY(this, translationY);
        break;
      case MotionEvent.ACTION_UP:
        break;
    }
    mLastX = x;
    mLastY = y;
    return true;
  }
}
