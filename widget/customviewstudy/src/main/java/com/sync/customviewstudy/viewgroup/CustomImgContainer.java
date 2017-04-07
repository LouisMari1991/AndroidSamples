package com.sync.customviewstudy.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.sync.logger.Logger;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月07日 09:59
 */
public class CustomImgContainer extends ViewGroup {

  public CustomImgContainer(Context context) {
    super(context);
    Logger.i(" constructor 1 ");
  }

  public CustomImgContainer(Context context, AttributeSet attrs) {
    super(context, attrs);
    Logger.i(" constructor 2 ");
  }

  /**
   * 1、决定该ViewGroup的LayoutParams
   * 对于我们这个例子，我们只需要ViewGroup能够支持margin即可，那么我们直接使用系统的MarginLayoutParams
   * <p>
   * 重写父类的该方法，返回MarginLayoutParams的实例，这样就为我们的ViewGroup指定了其LayoutParams为MarginLayoutParams。
   * <p>
   * 遍历所有的childView，根据childView的宽和高以及margin，然后分别将0，1，2，3位置的childView依次设置到左上、右上、左下、右下的位置。
   */
  @Override public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
    Logger.i(" generateLayoutParams , attrs : " + attrs);
    return new MarginLayoutParams(getContext(), attrs);
  }

  /**
   * 2、onMeasure
   * 在onMeasure中计算childView的测量值以及模式，以及设置自己的宽和高：
   * <p>
   * 计算所有childview 的宽度和高度 然后根据childview的计算结果,设置自己的宽高
   */
  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    Logger.i(" onMeasure ");

    int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

    // 计算出所有的childView的宽和高
    measureChildren(widthMeasureSpec, heightMeasureSpec);

    /**
     * 记录如果是wrap_content是设置的宽和高
     */
    int width = 0;
    int height = 0;

    // 获取子元素总数
    int childCount = getChildCount();

    int cWidth = 0;
    int cHeight = 0;

    MarginLayoutParams cParams = null;

    // 用于计算左边两个childView 的高度
    int lHeight = 0;
    // 用于计算右边两个childView 的高度，最终高度取两者之间的最大值
    int rHeight = 0;

    // 用于计算上面两个childView的宽度
    int tWidth = 0;
    // 用于计算下面两个childView的宽度，最终宽度取两者的最大值
    int bWidth = 0;

    /**
     * 根据childview计算出的宽和高,以及设置的margin计算容器的宽和高,主要用于容器是warp_content时
     */
    for (int i = 0; i < childCount; i++) {
      View childView = getChildAt(i);
      cWidth = childView.getMeasuredWidth();
      cHeight = childView.getMeasuredHeight();
      cParams = (MarginLayoutParams) childView.getLayoutParams();

      // 上面两个childVIew
      if (i == 0 || i == 1) {
        tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
      }

      if (i == 2 || i == 3) {
        bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
      }

      if (i == 0 || i == 2) {
        lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
      }

      if (i == 1 || i == 3) {
        rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
      }
    }

    width = Math.max(tWidth, bWidth);
    height = Math.max(lHeight, rHeight);

    setMeasuredDimension((widthSpecMode == MeasureSpec.EXACTLY) ? widthSpecSize : width,
        (heightSpecMode == MeasureSpec.EXACTLY) ? heightSpecSize : height);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    Logger.i(" onLayout ");

    int childCount = getChildCount();
    int childWidth = 0;
    int childHeight = 0;
    MarginLayoutParams childParams = null;

    /**
     * 遍历所有childView根据其宽和高，以及margin进行布局
     */
    for (int i = 0; i < childCount; i++) {
      View childView = getChildAt(i);
      childWidth = childView.getMeasuredWidth();
      childHeight = childView.getMeasuredHeight();
      childParams = (MarginLayoutParams) childView.getLayoutParams();

      int cl = 0, ct = 0, cr = 0, cb = 0;
      switch (i) {
        case 0: {
          cl = childParams.leftMargin;
          ct = childParams.topMargin;
        }
        break;
        case 1: {
          cl = getWidth() - childWidth - childParams.rightMargin;
          ct = childParams.topMargin;
        }
        break;
        case 2: {
          cl = childParams.leftMargin;
          ct = getHeight() - childHeight - childParams.bottomMargin;
        }
        break;
        case 3: {
          cl = getWidth() - childWidth - childParams.rightMargin;
          ct = getHeight() - childHeight - childParams.bottomMargin;
        }
        break;
      }
      cr = cl + childWidth;
      cb = ct + childHeight;
      childView.layout(cl, ct, cr, cb);
    }
  }
}






















