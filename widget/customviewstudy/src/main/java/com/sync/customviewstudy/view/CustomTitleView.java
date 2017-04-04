package com.sync.customviewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import com.sync.customviewstudy.R;
import java.util.HashSet;
import java.util.Random;

/**
 * Description:
 * Author：SYNC on 2017/4/2 0002 21:47
 * Contact：289168296@qq.com
 */
public class CustomTitleView extends View {

  private String mTitleText; // 文字
  private int    mTitleTextColor; // 文字颜色
  private int    mTitleTextSize; // 字号

  private Paint mPaint;
  private Rect  mRect;

  public CustomTitleView(Context context) {
    this(context, null);
  }

  public CustomTitleView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    /**
     * 获取自定义的样式属性
     */
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
    mTitleText = typedArray.getString(R.styleable.CustomTitleView_titleText);
    mTitleTextColor = typedArray.getColor(R.styleable.CustomTitleView_titleTextColor, Color.BLACK);
    // 默认设置为16sp,TypedValue也可以把sp转化为px
    mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTitleView_titleTextSize,
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
    typedArray.recycle();

    /**
     * 获得绘制文本的宽和高
     */
    mPaint = new Paint();
    mPaint.setTextSize(mTitleTextSize);
    mPaint.setColor(mTitleTextColor);
    mRect = new Rect();
    mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRect);

    Log.e("---->", " mTitleText : " + mTitleText);
    Log.e("---->", " mTitleTextColor : " + mTitleTextColor);
    Log.e("---->", " mTitleTextSize : " + mTitleTextSize);

    setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        mTitleText = randomText();
        postInvalidate();
      }
    });
  }

  private String randomText() {
    Random random = new Random();
    HashSet<Integer> integers = new HashSet<>();
    while (integers.size() < 4) {
      int randomInt = random.nextInt(10);
      integers.add(randomInt);
    }
    StringBuffer sb = new StringBuffer();
    for (Integer i : integers) {
      sb.append("").append(i);
    }
    return sb.toString();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthSpecMoe = MeasureSpec.getMode(widthMeasureSpec);
    int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

    int width = 0;
    int height = 0;

    switch (widthSpecMoe) {
      case MeasureSpec.EXACTLY: // 精准模式
        width = getPaddingLeft() + getPaddingRight() + widthSpecSize;
        break;
      case MeasureSpec.AT_MOST: // 最大化模式
      case MeasureSpec.UNSPECIFIED:
        float textWidth = mPaint.measureText(mTitleText);
        width = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
        break;
    }

    switch (heightSpecMode) {
      case MeasureSpec.EXACTLY: // 精准模式
        height = getPaddingTop() + getPaddingBottom() + heightSpecSize;
        break;
      case MeasureSpec.AT_MOST: // 最大化模式
      case MeasureSpec.UNSPECIFIED:
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float textHeight = Math.abs((fontMetrics.bottom - fontMetrics.top));
        height = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
        break;
    }

    setMeasuredDimension(width, height);
  }

  @Override protected void onDraw(Canvas canvas) {
    mPaint.setColor(Color.YELLOW);
    mPaint.setAntiAlias(true);
    mPaint.setTextAlign(Paint.Align.LEFT);
    // 画布,左上右下
    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint); // getMeasuredWidth()也可以。
    // 画笔
    mPaint.setColor(mTitleTextColor);
    // 画布,画Text  *****
    /**
     * 用的是其中一个drawText重载方法:canvas.drawText(String text,float x,float y,Paint paint);
     * x和y是绘制时的起点坐标, getWidth() / 2 - mRect.width() / 2 其实是为了居中绘制文本,
     * getWidth()是获取自定义View的宽度,
     * mRect.width()是获取文本的宽度,
     * 你可能是想问为什么不是直接getWidth() / 2吧?
     * 这样的话文本就是从水平方向中间位置向右绘制,绘制的文本当然就不是居中了,要减去mRect.width() / 2才是水平居中.垂直方向同理.
     *
     * "0 + getPaddingLeft()": 绘制文本的起点X
     *   "0": 直接从"0"开始就可以(文字会自带一点默认间距)
     *
     * */
    Log.e("---->", "getWidth():" + getWidth());
    Log.e("---->", "mRect.width():" + mRect.width());
    // 这样写会自动居中(但会有一点误差,以为文字会自带一点默认间距," - mRect.left"就好,csdn:[Zohar_zou]解决)
    canvas.drawText(mTitleText, getWidth() / 2 - mRect.width() / 2 - mRect.left,
        getHeight() / 2 + mRect.height() / 2, mPaint);
    // 不会居中
    //  canvas.drawText(mTitleText, 0 + getPaddingLeft(), getHeight() / 2 + mRect.height() / 2, mPaint);
    //  canvas.drawText(mTitleText, getPaddingLeft(), getHeight() / 2 + mRect.height() / 2, mPaint);
  }
}
