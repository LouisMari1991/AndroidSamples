package com.sync.toolbartitleanimation.ui;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.sync.toolbartitleanimation.R;

/**
 * Author：Administrator on 2017/3/17 0017 20:16
 * Contact：289168296@qq.com
 */
public class CircleIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

  private final int DEFAULT_INDICATOR_WIDTH = 5;

  private ViewPager mViewPager;
  private int mIndicatorMargin = -1;
  private int mIndicatorWidth = -1;
  private int mIndicatorHeight = -1;
  private int mAnimatorResId = R.animator.scale_with_alpha;
  private int mAnimatorReverseResId = 0;
  private int mIndicatorBackgroundResId = R.drawable.white_radius;
  private int mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
  private int mCurrentPosition = 0;
  private Animator mAnimatorOut;
  private Animator mAnimatorIn;

  public CircleIndicator(Context context) {
    super(context);
    init(context, null);
  }

  public CircleIndicator(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER);
    handleTypeArray(context, attrs);
    checkIndicatorConfig(context);
  }

  private void handleTypeArray(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
    mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
    mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
    mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
    mAnimatorResId = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
    mAnimatorReverseResId = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
    mIndicatorBackgroundResId = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
    mIndicatorUnselectedBackgroundResId = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
    typedArray.recycle();
  }

  public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
    configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin,
        R.animator.scale_with_alpha, 0, R.drawable.white_radius, R.drawable.white_radius);
  }

  public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin,
      @AnimatorRes int animatorId, @AnimatorRes int animatorReverseId,
      @DrawableRes int indicatorBackgroundId,
      @DrawableRes int indicatorUnselectedBackgroundId) {
    mIndicatorWidth = indicatorWidth;
    mIndicatorHeight = indicatorHeight;
    mIndicatorMargin = indicatorMargin;

    mAnimatorResId = animatorId;
    mAnimatorReverseResId = animatorReverseId;
    mIndicatorBackgroundResId = indicatorBackgroundId;
    mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;

    checkIndicatorConfig(getContext());
  }

  private void checkIndicatorConfig(Context context) {

  }

  @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {

  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  public int dip2px(float dpValue) {
    final float scale = getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

}

















