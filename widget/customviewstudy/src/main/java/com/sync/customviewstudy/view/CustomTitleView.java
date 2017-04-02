package com.sync.customviewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
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
}
