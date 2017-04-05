package com.sync.dragrecyclerview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sync.dragrecyclerview.drag.DragHolderCallBack;
import com.sync.dragrecyclerview.drag.RecycleCallBack;
import java.util.List;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月05日 14:38
 */
public class DragAdapter extends RecyclerView.Adapter<DragAdapter.DragHolder> {

  private List<String> list;

  private RecycleCallBack mRecycleCallBack;
  public SparseArray<Integer> show = new SparseArray<>();

  public DragAdapter(RecycleCallBack callBack, List<String> data) {
    this.list = data;
    mRecycleCallBack = callBack;
  }

  public void setData(List<String> data) {
    this.list = data;
  }

  @Override public DragHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
    return new DragHolder(v, mRecycleCallBack);
  }

  @Override public void onBindViewHolder(DragHolder holder, int position) {
    holder.text.setText(list.get(position));
    if (null == show.get(position)) {
      holder.del.setVisibility(View.INVISIBLE);
    } else {
      holder.del.setVisibility(View.VISIBLE);
    }
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public class DragHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DragHolderCallBack {

    public  TextView        text;
    public  ImageView       del;
    public  RelativeLayout  item;
    private RecycleCallBack mCallBack;

    public DragHolder(View itemView, RecycleCallBack callBack) {
      super(itemView);
      this.mCallBack = callBack;
      item = (RelativeLayout) itemView.findViewById(R.id.item);
      text = (TextView) itemView.findViewById(R.id.text);
      del = (ImageView) itemView.findViewById(R.id.del);
      item.setOnClickListener(this);
      del.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
      if (null != mCallBack) {
        show.clear();
        mCallBack.onItemClick(getAdapterPosition(), v);
      }
    }

    @Override public void onSelect() {
      show.clear();
      show.put(getAdapterPosition(), getAdapterPosition());
      itemView.setBackgroundColor(Color.LTGRAY);
      del.setVisibility(View.VISIBLE);
    }

    @Override public void onClear() {
      itemView.setBackgroundResource(R.drawable.right_bottom_view);
      notifyDataSetChanged();
    }
  }
}
