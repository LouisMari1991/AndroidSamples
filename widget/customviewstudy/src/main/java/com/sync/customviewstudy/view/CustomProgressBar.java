package com.sync.customviewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.sync.customviewstudy.R;
import com.sync.logger.Logger;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月06日 13:31
 */
public class CustomProgressBar extends View {

  private int mFirstColor;
  private int mSecondColor;
  private int mCircleWidth;
  private int mSpeed;

  private int mProgress;

  private Paint   mPaint;
  private boolean isNext;
  private boolean isContinue;

  public CustomProgressBar(Context context) {
    this(context, null);
  }

  public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
    mFirstColor = typedArray.getColor(R.styleable.CustomProgressBar_firstColor, Color.GREEN);
    mSecondColor = typedArray.getColor(R.styleable.CustomProgressBar_secondColor, Color.RED);
    mCircleWidth = typedArray.getDimensionPixelSize(R.styleable.CustomProgressBar_circleWidth,
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
    mSpeed = typedArray.getInt(R.styleable.CustomProgressBar_speed, 20);
    typedArray.recycle();

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    isContinue = true;
    new Thread(new Runnable() {
      @Override public void run() {
        while (isContinue) {
          mProgress++;
          if (mProgress == 360) {
            mProgress = 0;
            isNext = !isNext;
          }
          postInvalidate();
          try {
            Thread.sleep(100 / mSpeed);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  // 用来关闭线程
  public void setContinue(boolean aContinue) {
    isContinue = aContinue;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

    Logger.i(getContext().getResources().getDimension(R.dimen.width) + "");
    Logger.i(getContext().getResources().getDimension(R.dimen.width1) + "");

    if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(600, 600);
    } else if (widthSpecMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(heightSpecSize, heightSpecSize);
    } else if (heightSpecMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(widthSpecSize, widthSpecSize);
    } else {
      int size = Math.min(widthMeasureSpec, heightMeasureSpec);
      setMeasuredDimension(size, size);
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int center = getWidth() / 2; // 圆心
    int radius = center - mCircleWidth / 2; // 半径
    mPaint.setStrokeWidth(mCircleWidth);
    mPaint.setStyle(Paint.Style.STROKE);
    RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);// 用于定义的圆弧的形状和大小的界限

    if (!isNext) { // 第一颜色完整，第二颜色跑
      mPaint.setColor(mFirstColor);
      canvas.drawCircle(center, center, radius, mPaint);
      mPaint.setColor(mSecondColor);
      canvas.drawArc(oval, -90, mProgress, false, mPaint); // 第四个参数表示是否画圆弧边框
    } else {
      mPaint.setColor(mSecondColor);
      canvas.drawCircle(center, center, radius, mPaint);
      mPaint.setColor(mFirstColor);
      canvas.drawArc(oval, -90, mProgress, false, mPaint);
    }
  }
}
