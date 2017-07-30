package com.sync.customviewstudy.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 自定义流式布局,来自慕课网:http://www.imooc.com/learn/237
 * Author：Mari on 2017-07-30 19:24
 * Contact：289168296@qq.com
 */
public class FlowLayout extends ViewGroup {

  public FlowLayout(Context context) {
    this(context, null);
  }

  public FlowLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * 存储所有的子View
   */
  private List<List<View>> mAllViews   = new ArrayList<>();
  /**
   * 每一行的高度
   */
  private List<Integer>    mLineHeight = new ArrayList<>();

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
    int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

    int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
    int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

    // wrap_content
    int width = 0;
    int height = 0;

    // 记录每一行的高度和宽度
    int lineWidth = 0;
    int lineHeight = 0;

    // 得到内部元素的个数
    int childCount = getChildCount();

    for (int i = 0; i < childCount; i++) {

      View child = getChildAt(i);

      // 测量子View宽和高
      measureChild(child, widthMeasureSpec, heightMeasureSpec);
      // 得到子View的LayoutParams
      MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

      // 子View占据的宽度
      int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
      // 子View占据的高度
      int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

      if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {  // 换行
        // 对比得到最大的宽度
        width = Math.max(width, lineWidth);
        // 重置lineWidth
        lineWidth = childWidth;
        // 记录总的行高
        height += lineHeight;
        // 记录当前行高
        lineHeight = childHeight;
      } else { // 未换行情况
        // 叠加行宽
        lineWidth += childWidth;
        // 得到当前行的最大高度
        lineHeight = Math.max(lineHeight, childHeight);
      }

      // 最后一个控件,无论是换行还是原行都需要处理. 记录总的高度
      if (i == childCount - 1) {
        width = Math.max(lineWidth, width);
        height += lineHeight;
      }
    }

    //Logger.i("sizeWidth : " + sizeWidth);
    //Logger.i("sizeHeight : " + sizeHeight);
    //Logger.i("width : " + width);
    //Logger.i("height : " + height);

    setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
        modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    mAllViews.clear();
    mLineHeight.clear();

    // 当前ViewGroup的宽度
    int width = getWidth();

    int lineWidth = 0;
    int lineHeight = 0;

    List<View> lineViews = new ArrayList<>();
    int childCount = getChildCount();

    for (int i = 0; i < childCount; i++) {
      View child = getChildAt(i);
      MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

      int childWidth = child.getMeasuredWidth();
      int childHeight = child.getMeasuredHeight();

      // 如果需要换行
      if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
        // 记录当前行LineHeight
        mLineHeight.add(lineHeight);
        // 记录当前行所有的子View
        mAllViews.add(lineViews);

        // 重置行宽和行高
        lineWidth = 0;
        lineHeight = childHeight + lp.topMargin + lp.bottomMargin;

        // 重置View集合
        lineViews = new ArrayList<>();
      } // if end

      // 不需要换行

      // 记录当前行的宽度总值
      lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
      // 记录当前行的最大高度
      lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);

      // 保存当前行的View
      lineViews.add(child);
    } // for end

    // 处理最后一行
    mLineHeight.add(lineHeight);
    mAllViews.add(lineViews);

    // 设置子View的位置

    int left = getPaddingLeft();
    int top = getPaddingTop();

    // 总的行数
    int lineNum = mAllViews.size();

    for (int i = 0; i < lineNum; i++) {
      // 当前行所有的View
      lineViews = mAllViews.get(i);
      lineHeight = mLineHeight.get(i);

      for (int j = 0; j < lineViews.size(); j++) { // 遍历当前行所有View
        View child = lineViews.get(j);
        // 判断child状态
        if (child.getVisibility() == View.GONE) {
          continue;
        }

        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        int lc = left + lp.leftMargin;
        int tc = top + lp.topMargin;
        int rc = lc + child.getMeasuredWidth();
        int bc = tc + child.getMeasuredHeight();

        // 为子View进行布局
        child.layout(lc, tc, rc, bc);

        // 当前行的宽度累加
        left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
      }

      // 下一行的时候,重置left,top
      left = getPaddingLeft();
      top += lineHeight;
    }
  }

  /**
   * 当前ViewGroup对应的LayoutParams
   */
  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(), attrs);
  }
}
