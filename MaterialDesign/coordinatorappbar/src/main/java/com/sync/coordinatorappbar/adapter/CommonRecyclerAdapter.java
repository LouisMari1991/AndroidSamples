package com.sync.coordinatorappbar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Description:
 * Author：Mari on 2017-08-08 20:37
 * Contact：289168296@qq.com
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerHolder> {

  private Context        context;
  private List<T>        list;
  private LayoutInflater inflater;
  private int            itemLayoutId; // 布局id
  private boolean        isScrolling;// 是否移动
  private RecyclerView   recyclerView;

  private OnItemClickListener     listener;//点击事件监听器
  private OnItemLongClickListener longClickListener;//长按监听器

  //在RecyclerView提供数据的时候调用
  @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    this.recyclerView = recyclerView;
  }

  @Override public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
    this.recyclerView = null;
  }

  public interface OnItemClickListener {
    void onItemClick(RecyclerView parent, View view, int position);
  }

  public interface OnItemLongClickListener {
    boolean onItemLongClick(RecyclerView parent, View view, int position);
  }

  /**
   * 插入一项
   */
  public void insert(T item, int position) {
    list.add(position, item);
    notifyItemInserted(position);
  }

  /**
   * 删除一项
   *
   * @param position 删除位置
   */
  public void delete(int position) {
    list.remove(position);
    notifyItemRemoved(position);
  }

  public CommonRecyclerAdapter(Context context, List<T> list, int itemLayoutId) {
    this.context = context;
    this.list = list;
    this.itemLayoutId = itemLayoutId;
    inflater = LayoutInflater.from(context);
  }

  @Override public CommonRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(itemLayoutId, parent, false);
    return CommonRecyclerHolder.getRecyclerHolder(context, view);
  }

  @Override public void onBindViewHolder(final CommonRecyclerHolder holder, int position) {
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (listener != null && v != null && recyclerView != null) {
          int position = recyclerView.getChildAdapterPosition(v);
          listener.onItemClick(recyclerView, v, position);
        }
      }
    });

    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        if (longClickListener != null && v != null && recyclerView != null) {
          int position = recyclerView.getChildAdapterPosition(v);
          longClickListener.onItemLongClick(recyclerView, v, position);
          return true;
        }
        return false;
      }
    });

    convert(holder, list.get(position), position, isScrolling);
  }

  @Override public int getItemCount() {
    return list == null ? 0 : list.size();
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
    this.longClickListener = longClickListener;
  }

  /**
   * 填充RecyclerView适配器的方法，子类需要重写
   *
   * @param holder ViewHolder
   * @param item 子项
   * @param position 位置
   * @param isScrolling 是否在滑动
   */
  public abstract void convert(CommonRecyclerHolder holder, T item, int position, boolean isScrolling);
}
