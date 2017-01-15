package com.sync.materialdesign.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.sync.materialdesign.R;

/**
 * Author：Administrator on 2017/1/15 0015 15:06
 * Contact：289168296@qq.com
 */
public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
  public TextView mTextView;
  public ImageView mImageView;

  public MyRecyclerViewHolder(View itemView) {
    super(itemView);
    mTextView = (TextView) itemView.findViewById(R.id.content);
    mImageView = (ImageView) itemView.findViewById(R.id.image_id);
  }
}
