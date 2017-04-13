package com.googlesamples.unsplash.ui.grid;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author：Administrator on 2016/9/1 0001 21:11
 * Contact：289168296@qq.com
 */
public class GridMarginDecoration extends RecyclerView.ItemDecoration {

  private int space;

  public GridMarginDecoration(int space) {
    this.space = space;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    outRect.left = space;
    outRect.top = space;
    outRect.right = space;
    outRect.bottom = space;
  }
}
