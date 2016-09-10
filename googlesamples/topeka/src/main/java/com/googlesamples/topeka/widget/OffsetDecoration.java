package com.googlesamples.topeka.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by YH on 2016/9/10.
 */
public class OffsetDecoration extends RecyclerView.ItemDecoration {

  private final int mOffset;

  public OffsetDecoration(int offset) {
    this.mOffset = offset;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.left = mOffset;
    outRect.right = mOffset;
    outRect.bottom = mOffset;
    outRect.top = mOffset;
  }
}
