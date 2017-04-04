package com.sync.customviewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.sync.customviewstudy.R;

/**
 * Description:
 * Author：SYNC on 2017/4/4 0004 19:41
 * Contact：289168296@qq.com
 */
public class CustomImageView extends View {

  private Bitmap mBitmap; // 图片
  private int    mImageScaleType;
  private String mTitleText;
  private int    mTitleTextSieze;
  private int    mTitleTextColor;
  private Rect mRect;
  private Rect mTextBound;

  public CustomImageView(Context context) {
    this(context, null);
  }

  public CustomImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
  }
}
