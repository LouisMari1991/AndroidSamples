package com.sync.materialdesign.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author：Administrator on 2017/1/15 0015 15:45
 * Contact：289168296@qq.com
 */
public class MyStaggeredViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

  public interface OnItemClickListener {
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
  }

  public OnItemClickListener mOnItemClickListener;

  public void setmOnItemClickListener(OnItemClickListener listener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  @Override public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }
}
