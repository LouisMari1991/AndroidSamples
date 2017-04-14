package com.sync.base.widget;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YH on 2017-01-16.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

  protected List<T> datas = new ArrayList<>();
  protected OnItemClickListener<T>     onItemClickListener;
  protected OnItemLongClickListener<T> onItemLongClickListener;

  @Override public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
    holder.onBaseBindViewHolder(datas.get(position), position);
  }

  @Override public int getItemCount() {
    return datas.size();
  }

  public void add(T t) {
    this.datas.add(t);
  }

  public void addFirst(T t) {
    this.datas.add(0, t);
  }

  public void addAll(List<T> datas) {
    this.datas.addAll(datas);
  }

  public void remove(int position) {
    this.datas.remove(position);
  }

  public void clear() {
    this.datas.clear();
  }

  public void setOnItemClickListener(OnItemClickListener<T> listener) {
    this.onItemClickListener = listener;
  }

  public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
    this.onItemLongClickListener = listener;
  }
}