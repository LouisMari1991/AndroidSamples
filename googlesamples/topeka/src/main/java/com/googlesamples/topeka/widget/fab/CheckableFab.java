package com.googlesamples.topeka.widget.fab;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * Created by YH on 2017-02-23.
 */

public class CheckableFab extends FloatingActionButton implements Checkable {

  private boolean mIsChecked = false;

  public CheckableFab(Context context) {
    this(context, null);
  }

  public CheckableFab(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CheckableFab(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //setImageResource(R.drawable.answer_quiz_fab);
  }

  @Override public void setChecked(boolean checked) {
    if (mIsChecked == checked) {
      return;
    }
    mIsChecked = checked;
    refreshDrawableState(); // refreshDrawableState()刷新该ViewGroup自身的背景图。
  }

  @Override public boolean isChecked() {
    return mIsChecked;
  }

  @Override public void toggle() {
    setChecked(!mIsChecked);
  }
}
