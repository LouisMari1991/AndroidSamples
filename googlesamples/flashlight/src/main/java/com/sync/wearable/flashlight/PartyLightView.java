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

      }
    };
  }

  @Override protected void onDraw(Canvas canvas) {
    canvas.drawColor(mCurrentColor);
    super.onDraw(canvas);
  }
}
