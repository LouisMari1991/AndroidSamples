package com.sync.dragrecyclerview.drag;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月05日 14:28
 */
public class DragItemCallBack extends ItemTouchHelper.Callback {

  private RecycleCallBack mCallBack;

  public DragItemCallBack(RecycleCallBack callBack) {
    mCallBack = callBack;
  }

  @Override public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
    int action = ItemTouchHelper.ACTION_STATE_IDLE | ItemTouchHelper.ACTION_STATE_DRAG;
    return makeMovementFlags(dragFlags, 0);
  }

  @Override
  public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    int start = viewHolder.getAdapterPosition();
    int end = target.getAdapterPosition();
    mCallBack.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    return true;
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

  }

  @Override public boolean isLongPressDragEnabled() {
    return true;
  }

  @Override public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    super.onSelectedChanged(viewHolder, actionState);
    if (viewHolder instanceof DragHolderCallBack){
      DragHolderCallBack holder = (DragHolderCallBack) viewHolder;
      holder.onSelect();
    }
  }

  @Override public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    super.clearView(recyclerView, viewHolder);
    if (viewHolder instanceof DragHolderCallBack) {
      DragHolderCallBack holder = (DragHolderCallBack) viewHolder;
      holder.onClear();
    }
  }
}
