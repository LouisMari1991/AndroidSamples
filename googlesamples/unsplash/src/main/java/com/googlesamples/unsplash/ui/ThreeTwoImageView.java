package com.googlesamples.unsplash.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by YH on 2016-11-30.
 */

public class ThreeTwoImageView extends ForegroundImageView {
  public ThreeTwoImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = MeasureSpec.getSize(widthMeasureSpec);
    int desiredHeight = width * 2 / 3;
    super.onMeasure(widthMeasureSpec,
        MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY));
  }
}
