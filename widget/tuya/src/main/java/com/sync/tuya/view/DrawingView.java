package com.sync.tuya.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.sync.logger.Logger;

/**
 * Created by YH on 2017-03-16.
 */

public class DrawingView extends View {

  private Paint mPaint;
  private Path mPath;
  private Bitmap mBitmap;
  private Canvas mChacheCanvas;

  private float preX;
  private float preY;

  private int screenWidth;
  private int screenHeight;

  public DrawingView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public void setPaintColor(int color) {
    mPaint.setColor(color);
  }


  public void setPaintWidth(int width) {
    mPaint.setStrokeWidth((float) width);
  }

  public void clearCanvas() {
    mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
    mChacheCanvas.setBitmap(mBitmap);
    invalidate();
  }

  public Bitmap getBitmap() {
    return mBitmap;
  }

  private void init(Context context) {
    screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    screenHeight = context.getResources().getDisplayMetrics().heightPixels;
    mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
    mChacheCanvas = new Canvas();
    mChacheCanvas.setBitmap(mBitmap);
    mPath = new Path();
    mPaint = new Paint();
    mPaint.setColor(Color.BLACK);
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(1.0f);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN: {
        mPath.moveTo(x, y);
        preX = x;
        preY = y;
        break;
      }
      case MotionEvent.ACTION_UP: {
        mChacheCanvas.drawPath(mPath, mPaint);
        mPath.reset();
        break;
      }
      case MotionEvent.ACTION_MOVE: {
        float dx = Math.abs(preX - x);
        float dy = Math.abs(preY - y);
        if (dx > 10.0f || dy > 10.0f) {
          mPath.quadTo(preX, preY, x, y);
          this.preX = x;
          this.preY = y;
          break;
        }
        break;
      }
    }
    invalidate();
    return true;
  }

  @Override protected void onDraw(Canvas canvas) {
    Logger.i("onDraw : " + canvas);
    canvas.drawBitmap(mBitmap, 0.0f, 0.0f, mPaint);
    canvas.drawPath(mPath, mPaint);
    super.onDraw(canvas);
  }
}
