package com.sync.scrollshapeui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sync.scrollshapeui.R;
import com.sync.scrollshapeui.utils.CommonUtils;

/**
 * Description:
 * Author：Mari on 2017-08-05 20:44
 * Contact：289168296@qq.com
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.NormalTextViewHolder> {

  private final LayoutInflater mInflater;
  private String[] mTitles = null;

  public ListAdapter(Context context) {
    mTitles = CommonUtils.getStringArray(R.array.books);
    mInflater = LayoutInflater.from(context);
  }

  @Override public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new NormalTextViewHolder(mInflater.inflate(R.layout.item_list, parent, false));
  }

  @Override public void onBindViewHolder(NormalTextViewHolder holder, int position) {
    holder.mTextView.setText(mTitles[position]);
  }

  @Override public int getItemCount() {
    return mTitles == null ? 0 : mTitles.length;
  }

  class NormalTextViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;

    public NormalTextViewHolder(View itemView) {
      super(itemView);
      mTextView = (TextView) itemView.findViewById(R.id.tv_text);
    }
  }
}
