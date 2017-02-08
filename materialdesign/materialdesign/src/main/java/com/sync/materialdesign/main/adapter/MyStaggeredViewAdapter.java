package com.sync.materialdesign.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sync.materialdesign.R;
import java.util.ArrayList;
import java.util.List;

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

  private Context mContext;
  public List<String> mDatas;
  public List<Integer> mHeights;
  public LayoutInflater mLayoutInflater;

  public MyStaggeredViewAdapter(Context mContext) {
    this.mContext = mContext;
    mLayoutInflater = LayoutInflater.from(mContext);
    mDatas = new ArrayList<>();
    mHeights = new ArrayList<>();
    for (int i = 'A'; i <= 'z'; i++) {
      mDatas.add((char) i + "");
    }
    for (int i = 0; i < mDatas.size(); i++) {
      mHeights.add((int) (Math.random() * 300) + 200);
    }
  }

  @Override public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = mLayoutInflater.inflate(R.layout.item_main, parent, false);
    MyRecyclerViewHolder holder = new MyRecyclerViewHolder(v);
    return holder;
  }

  @Override public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          mOnItemClickListener.onItemLongClick(holder.itemView, position);
        }
      });
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View view) {
          mOnItemClickListener.onItemLongClick(holder.itemView, position);
          return true;
        }
      });
    }
  }

  @Override public int getItemCount() {
    return mDatas.size();
  }
}
