package com.sync.wearable.flashlight;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YH on 2017-02-13.
 */

public class PartyLightView extends View {

  private int[] mColors = {
      Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA
  };

  private int mFromColorIndex;
  private int mToColorIndex;

  /**
   * Value b/t 0 and 1.
   */
  private float mProgress;

  private ArgbEvaluator mEvaluator;

  private int mCurrentColor;

  private Handler mHandler;

  public PartyLightView(Context context) {
    super(context);
    init();
  }

  public PartyLightView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PartyLightView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mEvaluator = new ArgbEvaluator();
    mHandler = new Handler() {
      @Override public void handleMessage(Message msg) {
        mCurrentColor = getColor(mProgress, mColors[mFromColorIndex], mColors[mToColorIndex]);
        postInvalidate();
        mProgress += 0.1;
        if (mProgress > 1) {
          mFromColorIndex = mToColorIndex;
          // Find a new color
          mToColorIndex++;
          if (mToColorIndex >= mColors.length) {
            mToColorIndex = 0;
          }
        }
        mHandler.sendEmptyMessageDelayed(0, 100);
      }
    };
  }

  public void startCycling() {
    mHandler.sendEmptyMessage(0);
  }

  public void stopCycling() {
    mHandler.removeMessages(0);
  }

  private int getColor(float fraction, int colorStart, int colorEnd) {
    int startInt = colorStart;
    int startA = (startInt >> 24) & 0xff;
    int startR = (startInt >> 16) & 0xff;
    int startG = (startInt >> 8) & 0xff;
    int startB = startInt & 0xff;

    int endInt = colorEnd;
    int endA = (endInt >> 24) & 0xff;
    int endR = (endInt >> 16) & 0xff;
    int endG = (endInt >> 8) & 0xff;
    int endB = endInt & 0xff;

    return (startA + (int) (fraction * (endA - startA))) << 24 |
        (startR + (int) (fraction * (endR - startR))) << 16 |
        (startG + (int) (fraction * (endG - startG))) << 8 |
        ((startB + (int) (fraction * (endB - startB))));
  }

  @Override protected void onDraw(Canvas canvas) {
    canvas.drawColor(mCurrentColor);
    super.onDraw(canvas);
  }
}
