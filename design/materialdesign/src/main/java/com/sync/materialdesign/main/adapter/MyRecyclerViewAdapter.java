package com.sync.materialdesign.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sync.materialdesign.R;
import com.sync.materialdesign.detail.Book;
import com.sync.materialdesign.detail.BookDetailActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Author：Administrator on 2017/1/15 0015 15:14
 * Contact：289168296@qq.com
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

  public interface OnItemClickListener {
    void onItemClick(View v, int position);

    void onItemLongClick(View v, int position);
  }

  private OnItemClickListener mOnItemClickListener;

  public void setOnItemClickListener(MyRecyclerViewAdapter.OnItemClickListener listener) {
    mOnItemClickListener = listener;
  }

  public Context mContext;
  public List<String> mDatas;
  public LayoutInflater mLayoutInflater;

  public MyRecyclerViewAdapter(Context mContext) {
    this.mContext = mContext;
    mLayoutInflater = LayoutInflater.from(mContext);
    mDatas = new ArrayList<>();
    for (int i = 'a'; i < 'z'; i++) {
      mDatas.add("加班宝 " + (char) i + " ");
    }
  }

  /**
   * 创建ViewHolder
   * 2017/1/15 0015 15:26
   */
  @Override public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = mLayoutInflater.inflate(R.layout.item_main, parent, false);
    MyRecyclerViewHolder holder = new MyRecyclerViewHolder(v);
    return holder;
  }

  /**
   * 绑定ViewHolder,给item中的控件设置数据
   * 2017/1/15 0015 16:05
   */
  @Override public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Book book = new Book();
          book.setTitle("中医");
          book.setSummary("内容简介");
          book.setAuthor_intro("作者简介");
          book.setCatalog("目录");
          Intent intent = new Intent(mContext, BookDetailActivity.class);
          ActivityOptionsCompat options =
              ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                  holder.mImageView, mContext.getString(R.string.transition_book_img));
          ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }
      });
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View view) {
          mOnItemClickListener.onItemLongClick(holder.itemView, position);
          return true;
        }
      });
    }
    holder.mTextView.setText(mDatas.get(position));
  }

  @Override public int getItemCount() {
    return mDatas.size();
  }
}
