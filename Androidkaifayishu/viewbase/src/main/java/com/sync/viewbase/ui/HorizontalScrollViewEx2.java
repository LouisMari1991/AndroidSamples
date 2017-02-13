package com.sync.viewbase.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import com.sync.logger.Logger;

/**
 * Created by YH on 2017-02-04.
 */

public class HorizontalScrollViewEx2 extends ViewGroup {

  private int mChildrenSize;
  private int mChilWidth;
  private int mChilIndex;

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
    Logger.i("onTouchEvent action : " + event.getAction());
    mVelocityTracker.addMovement(event);
    int x = (int) event.getX();
    int y = (int) event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN: {

        break;
      }
      case MotionEvent.ACTION_MOVE: {
        break;
      }
      case MotionEvent.ACTION_UP: {
        break;
      }
    }
    mLastX = x;
    mLastY = y;
    return true;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int mesuredWidth = 0;
    int mesuredHeight = 0;
    final int childCount = getChildCount();
    measureChildren(widthMeasureSpec, heightMeasureSpec);
    if (childCount == 0) {
      setMeasuredDimension(0, 0);
    }
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    Logger.i(getWidth() + "");
    int childLeft = 0;
    final int childCount = getChildCount();
    mChildrenSize = childCount;
    for (int i = 0; i < childCount; i++) {
      final View childView = getChildAt(i);
      if (childView.getVisibility() != View.GONE) {
        final int childWidth = childView.getMeasuredWidth();
        mChilWidth = childWidth;
        childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
        childLeft += childWidth;
      }
    }
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
