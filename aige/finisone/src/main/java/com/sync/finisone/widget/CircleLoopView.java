package com.sync.finisone.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.sync.finisone.utils.MeasureUtil;

/**
 * Created by YH on 2017-03-15.
 */

public class CircleLoopView extends View implements Runnable {

  private Paint mPaint;

  private int radiu;

  public CircleLoopView(Context context) {
    super(context);
    init();
  }

  public CircleLoopView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CircleLoopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /*
     * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
     *
     * 画笔样式分三种：
     * 1.Paint.Style.STROKE：描边
     * 2.Paint.Style.FILL_AND_STROKE：描边并填充
     * 3.Paint.Style.FILL：填充
     */
    mPaint.setStyle(Paint.Style.STROKE);

    // 设置画笔颜色为浅灰色
    mPaint.setColor(Color.LTGRAY);

    /*
     * 设置描边的粗细，单位：像素px
     * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
     */
    mPaint.setStrokeWidth(10);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 绘制圆环
    canvas.drawCircle(MeasureUtil.getScreenSize((Activity) getContext())[0] / 2,
        MeasureUtil.getScreenSize((Activity) getContext())[1] / 2, radiu, mPaint);
  }

  @Override public void run() {
/*
     * 确保线程不断执行不断刷新界面
     */
    while (true) {
      try {
            /*
             * 如果半径小于200则自加否则大于200后重置半径值以实现往复
             */
        if (radiu <= 200) {
          radiu += 10;

          // 刷新View
          postInvalidate();
        } else {
          radiu = 0;
        }

        // 每执行一次暂停40毫秒
        Thread.sleep(40);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
