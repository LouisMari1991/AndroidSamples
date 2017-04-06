package com.sync.customviewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import com.sync.customviewstudy.R;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月06日 16:14
 */
public class CustomVolumeControlBar extends View {

  private int    mFirstColor;
  private int    mSecondColor;
  private int    mCircleWidth;
  private Bitmap mBitmap; // 中间的图片
  private int    mSplitSize; // 每个块之间的间隔
  private int    mCount; // 个数

  private int mCurrentCount = 3;

  private Paint mPaint;
  private Rect  mRect;

  public CustomVolumeControlBar(Context context) {
    this(context, null);
  }

  public CustomVolumeControlBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomVolumeControlBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumeControlBar, defStyleAttr, 0);
    mFirstColor = typedArray.getColor(R.styleable.CustomVolumeControlBar_vFirstColor, Color.BLACK);
    mSecondColor = typedArray.getColor(R.styleable.CustomVolumeControlBar_vSecondColor, Color.WHITE);
    mCircleWidth = typedArray.getDimensionPixelSize(R.styleable.CustomVolumeControlBar_vCircleWidth,
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
    mBitmap = BitmapFactory.decodeResource(getResources(),
        typedArray.getResourceId(R.styleable.CustomVolumeControlBar_bg, 0));
    mCount = typedArray.getInt(R.styleable.CustomVolumeControlBar_dotCount, 20);
    mSplitSize = typedArray.getInt(R.styleable.CustomVolumeControlBar_splitSize, 20);
    typedArray.recycle();

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mRect = new Rect();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

    if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(400, 400);
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
    mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
    mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头
    mPaint.setStyle(Paint.Style.STROKE); // 设置空心
    int center = getWidth() / 2; // 获取圆心的坐标
    int radius = center - mCircleWidth / 2; // 半径
    drawOval(canvas, center, radius); // 画椭圆块

    /**
     * 计算出内切正方形的位置
     */
    int relRadius = radius - mCircleWidth / 2; // 获取内圆的半径,即正方形四个顶点所在的圆

    // 获得正方形四个顶点所在的圆，计算出正方形的四个顶点
    /**
     * 内切正方形距离左边的距离(或顶部):
     * (内圆半径 -  (更2 / 2) * 内圆半径) + 圆弧的宽度
     */
    mRect.left = (int) (relRadius - Math.sqrt(2) / 2 * relRadius) + mCircleWidth;
    mRect.top = (int) (relRadius - Math.sqrt(2) / 2 * relRadius) + mCircleWidth;

    /**
     * 内切正方形距离左边的距离 + 正方形的边长(Math.sqrt(2) * relRadius)
     */
    mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);
    mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);

    /**
     * 如果图片比较小,那么根据图片的尺寸放置到正中心
     */
    if (mBitmap.getWidth() < Math.sqrt(2) * relRadius) {
      mRect.left = mCircleWidth + (relRadius - mBitmap.getWidth() / 2);
      mRect.top = mCircleWidth + (relRadius - mBitmap.getWidth() / 2);
      mRect.right = mCircleWidth + (relRadius + mBitmap.getWidth() / 2);
      mRect.bottom = mCircleWidth + (relRadius + mBitmap.getWidth() / 2);
    }
    canvas.drawBitmap(mBitmap, null, mRect, mPaint);
  }

  /**
   * 根据参数画出每个小块
   */
  private void drawOval(Canvas canvas, int center, int radius) {

    /**
     * 根据需要的个数以及间隙计算每个块块所占的比例 * 360  ---> 每个块的大小 ( 360 - 块个数 * 间距 ) / 块的个数
     */
    float itemSize = (360 * 1.0f - mCount * mSplitSize) / mCount; // 圆的长度先减去所有的间距之和，再除以块个数，得到块的长度
    // 用于定义的圆的形状和大小的界限
    RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);

    // 画圆环的第一种颜色
    mPaint.setColor(mFirstColor);
    for (int i = 0; i < mCount; i++) {
      canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
    }

    // 画第二种圆弧颜色
    mPaint.setColor(mSecondColor);
    for (int i = 0; i < mCurrentCount; i++) {
      canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        break;
      case MotionEvent.ACTION_UP:
        break;
    }
    return true;
  }
}
