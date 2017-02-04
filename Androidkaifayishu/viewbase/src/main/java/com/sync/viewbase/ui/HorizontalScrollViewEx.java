package com.sync.viewbase.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by YH on 2017-02-04.
 */

public class HorizontalScrollViewEx extends ViewGroup {

  private int mChildrenSize;
  private int mChildWidth;
  private int mChildIndex;

  private int mLastX;
  private int mLastY;

  private int mLastXIntercept;
  private int mLastYIntercept;

  private Scroller mScroller;
  private VelocityTracker mVelocityTracker;

  public HorizontalScrollViewEx(Context context) {
    super(context);
    init();
  }

  public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mScroller = new Scroller(getContext());
    mVelocityTracker = VelocityTracker.obtain();
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    boolean intercepted = false;
    int x = (int) ev.getX();
    int y = (int) ev.getY();
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN: {
        intercepted = false;
        if (!mScroller.isFinished()) {
          mScroller.abortAnimation();
          intercepted = true;
        }
        break;
      }
      case MotionEvent.ACTION_MOVE: {
        int deltaX = x - mLastXIntercept;
        int deltaY = y - mLastYIntercept;
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
          intercepted = true;
        } else {
          intercepted = false;
        }
        break;
      }
      case MotionEvent.ACTION_UP: {
        intercepted = false;
        break;
      }
    }
    mLastXIntercept = mLastX = x;
    mLastYIntercept = mLastY = y;
    return intercepted;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    mVelocityTracker.addMovement(event);
    int x = (int) event.getX();
    int y = (int) event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN: {
        if (!mScroller.isFinished()) {
          mScroller.abortAnimation();
        }
        break;
      }
      case MotionEvent.ACTION_MOVE: {
        int deltaX = x - mLastX;
        int deltaY = y - mLastY;
        scrollBy(-deltaX, 0);
        break;
      }
      case MotionEvent.ACTION_UP: {
        int scrollX = getScrollX();
        int scrollToChildIndex = scrollX / mChildWidth;
        mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = mVelocityTracker.getXVelocity();
        if (Math.abs(xVelocity) >= 50) {
          mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
        } else {
          mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
        }
        mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
        int dx = mChildIndex * mChildWidth - scrollX;
        smoothScrollBy(dx, 0);
        mVelocityTracker.clear();
        break;
      }
    }
    mLastX = x;
    mLastY = y;
    return true;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measuredWidth = 0;
    int measuredHeight = 0;
    final int childCount = getChildCount();
    measureChildren(widthMeasureSpec, heightMeasureSpec);

    int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
    int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
    if (childCount == 0) {
      setMeasuredDimension(0, 0);
    } else if (heightSpecMode == MeasureSpec.AT_MOST) {
      final View childView = getChildAt(0);

    } else if (widthSpecMode == MeasureSpec.AT_MOST) {
      final View childView = getChildAt(0);
    } else {
      final View childView = getChildAt(0);
    }
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
