package com.sync.viewbase.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by YH on 2017-02-04.
 */

public class HorizontalScrollViewEx2 extends ViewGroup {

  private int mChildrenSize;
  private int mChildrenWidth;
  private int mChildrenIndex;

  private int mLastX = 0;
  private int mLastY = 0;

  private int mLastInterceptX = 0;
  private int mLastInterceptY = 0;

  private Scroller mScroller;
  private VelocityTracker mVelocityTracker;

  public HorizontalScrollViewEx2(Context context) {
    super(context);
    init();
  }

  public HorizontalScrollViewEx2(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public HorizontalScrollViewEx2(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mScroller = new Scroller(getContext());
    mVelocityTracker = VelocityTracker.obtain();
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    return super.onInterceptTouchEvent(ev);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {

  }

  private void smoothScrollBy(int dx, int dy) {
    mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
    invalidate();
  }

  @Override public void computeScroll() {
    if (mScroller.computeScrollOffset()) {
      scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
      postInvalidate();
    }
  }

  @Override protected void onDetachedFromWindow() {
    mVelocityTracker.recycle();
    super.onDetachedFromWindow();
  }
}
