package com.sync.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.sync.logger.Logger;

/**
 * Description: 太极旋转图,核心是canvas移动到中央,来自:http://www.gcssloop.com/customview/taiji
 * Author：Mari on 2017-07-27 23:06
 * Contact：289168296@qq.com
 */
public class TaiJi extends View {

  public TaiJi(Context context) {
    this(context, null);
  }

  public TaiJi(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TaiJi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private Paint mWhilePaint;
  private Paint mBlackPaint;

  private float degrees = 0;
  private boolean isRun = true;

  private void init() {
    mWhilePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBlackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mWhilePaint.setColor(Color.WHITE);
    mBlackPaint.setColor(Color.BLACK);
    new Thread() {
      @Override public void run() {
        while (isRun) {
          degrees += 5;
          Logger.i(" run : " + degrees);
          if (degrees == 360) {
            degrees = 0;
          }
          postInvalidate();
          try {
            Thread.sleep(20);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      }
    }.start();
  }

  @Override protected void onDraw(Canvas canvas) {

    int width = canvas.getWidth();          //画布宽度
    int height = canvas.getHeight();        //画布高度

    canvas.translate(width / 2, height / 2);    // 移动坐标原点到画布中心
    canvas.drawColor(Color.GRAY); // 画背景色

    canvas.rotate(degrees);

    //绘制两个半圆
    int radius = Math.min(width, height) / 2 - 100;            //太极半径
    RectF rect = new RectF(-radius, -radius, radius, radius);   //绘制区域
    canvas.drawArc(rect, 90, 180, true, mBlackPaint);            //绘制黑色半圆
    canvas.drawArc(rect, -90, 180, true, mWhilePaint);           //绘制白色半圆

    int smallRadius = radius / 2; // 画两个更小的圆, 因为坐标已经在画布中心,所以x坐标为0
    canvas.drawCircle(0, -smallRadius, smallRadius, mBlackPaint);
    canvas.drawCircle(0, smallRadius, smallRadius, mWhilePaint);

    canvas.drawCircle(0, -smallRadius, smallRadius / 4, mWhilePaint);
    canvas.drawCircle(0, smallRadius, smallRadius / 4, mBlackPaint);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    isRun = false;
  }
}
