package com.sync.viewbase.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;
import com.sync.logger.Logger;

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
    int x = (int) ev.getX();
    int y = (int) ev.getY();
    int action = ev.getAction();
    if (action == MotionEvent.ACTION_DOWN) {
      mLastX = x;
      mLastY = y;
      if (!mScroller.isFinished()) {
        mScroller.abortAnimation();
        return true;
      }
      return false;
    } else {
      return true;
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    Logger.i(getWidth() + "");
    int childLeft= 0;
    final int childCount = getChildCount();
    mChildrenSize = childCount;
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
