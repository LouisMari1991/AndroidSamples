package com.sync.customviewstudy.viewgroup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月07日 14:14
 */
public class VDHLayout extends LinearLayout {

  private ViewDragHelper mViewDragHelper;

  public VDHLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    /**
     * 创建实例需要3个参数，第一个就是当前的ViewGroup，第二个sensitivity，主要用于设置touchSlop:
     */
    mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

      /**
       * 如何返回ture则表示可以捕获该view，你可以根据传入的第一个view参数决定哪些可以捕获
       */
      @Override public boolean tryCaptureView(View child, int pointerId) {
        return true;
      }

      /**
       * 可以在该方法中对child移动的边界进行控制，left,top 分别为即将移动到的位置，
       * 比如横向的情况下，我希望只在ViewGroup的内部移动，
       * 即：最小>=paddingLeft,最大<=ViewGroup.getWidth()-paddingRight-child.getWidth
       * 如直接返回left则会拖到边界外(针对于所有的View)
       * 2017/4/30 0030 16:40
       */
      @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
        return left;
      }

      @Override public int clampViewPositionVertical(View child, int top, int dy) {
        return top;
      }
    });
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    return mViewDragHelper.shouldInterceptTouchEvent(ev);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    mViewDragHelper.processTouchEvent(event);
    return true;
  }
}
