package com.sync.customviewstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.sync.logger.Logger;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月06日 18:06
 */
public class TestView extends View {

  Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

  public TestView(Context context) {
    super(context);
  }

  public TestView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setColor(Color.YELLOW);
    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        Logger.i(" ACTION_DOWN , x : " + x + " , y : " + y);
        break;
      case MotionEvent.ACTION_MOVE:
        Logger.i(" ACTION_MOVE , x : " + x + " , y : " + y);
        break;
      case MotionEvent.ACTION_UP:
        Logger.i(" ACTION_UP , x : " + x + " , y : " + y);
        //return true;
        break;
    }
    return super.onTouchEvent(event);
  }

}
