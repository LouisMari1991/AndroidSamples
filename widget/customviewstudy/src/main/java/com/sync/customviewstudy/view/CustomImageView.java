package com.sync.customviewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.sync.customviewstudy.R;

/**
 * Description:
 * Author：SYNC on 2017/4/4 0004 19:41
 * Contact：289168296@qq.com
 */
public class CustomImageView extends View {

  private Bitmap mBitmap; // 图片
  private int    mImageScaleType; // 图片缩放形式
  private String mTitleText; // 文字
  private int    mTitleTextSize; // 文字大小
  private int    mTitleTextColor; // 文字颜色

  private Rect  mRect; // 用来画图片所用
  private Rect  mTextBound; // 用来画文字所用
  private Paint mPaint; // 画笔
  private int   mWidth; // 宽
  private int   mHeight; // 高

  private int IMAGE_SCALE_FITXY  = 0;
  private int IMAGE_SCALE_CENTER = 1;

  public CustomImageView(Context context) {
    this(context, null);
  }

  public CustomImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
    mBitmap =
        BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(R.styleable.CustomImageView_image, -1));
    mImageScaleType = typedArray.getInt(R.styleable.CustomImageView_imageScaleType, -1);
    mTitleText = typedArray.getString(R.styleable.CustomImageView_imgTitleText);
    mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomImageView_imgTitleTextSize,
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
    mTitleTextColor = typedArray.getColor(R.styleable.CustomImageView_imgTitleTextColor, Color.BLACK);
    typedArray.recycle();

    mRect = new Rect();
    mTextBound = new Rect();
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setTextSize(mTitleTextSize);

    // 计算描绘字体需要的范围
    if (!TextUtils.isEmpty(mTitleText)) {
      mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthSpecMoe = MeasureSpec.getMode(widthMeasureSpec);
    int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

    if (widthSpecMoe == MeasureSpec.EXACTLY) { //精准模式
      mWidth = widthSpecSize;
    } else {
      int mImageWidth = getPaddingLeft() + getPaddingRight() + mBitmap.getWidth();
      int mTextWidth = getPaddingLeft() + getPaddingRight() + mTextBound.width();
      if (widthSpecMoe == MeasureSpec.AT_MOST) { // 最大化模式
        int des = Math.max(mImageWidth, mTextWidth);
        // 有可能的情况是占满全屏，为保险去掉最小的
        mWidth = Math.min(des, widthSpecSize);
      }
    }

    if (heightSpecMode == MeasureSpec.EXACTLY) { // 精准模式
      mHeight = heightSpecSize;
    } else {
      int des = getPaddingTop() + getPaddingBottom() + mBitmap.getHeight() + mTextBound.height();
      if (heightSpecMode == MeasureSpec.AT_MOST) { // 最大化模式
        // 有可能是全屏的,取最小的高度
        mHeight = Math.min(des, heightSpecSize);
      }
    }
    setMeasuredDimension(mWidth, mHeight);
  }

  @Override protected void onDraw(Canvas canvas) {
    mPaint.setStrokeWidth(4);
    mPaint.setStyle(Paint.Style.STROKE);
  }
}














































