package com.sync.coordinatorappbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Description:
 * Author：Mari on 2017-08-27 14:54
 * Contact：289168296@qq.com
 */
public class MyFragment extends Fragment {

  public static MyFragment newInstance() {
    MyFragment fragment = new MyFragment();
    return fragment;
  }

  RecyclerView         mRecyclerView;
  RecyclerView.Adapter mAdapter;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_list, container, false);
    initView(rootView);
    return rootView;
  }

  private void initView(View rootView) {
    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
    mRecyclerView.setLayoutManager(manager);
    mAdapter = new RecyclerView.Adapter() {
      @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new Holder(view);
      }

      @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Holder holder = (Holder) viewHolder;
        holder.mTextView.setText("current position: " + position);
      }

      @Override public int getItemCount() {
        return 30;
      }
    };
    mRecyclerView.setAdapter(mAdapter);
  }

  static class Holder extends RecyclerView.ViewHolder {
    TextView mTextView;

    public Holder(View itemView) {
      super(itemView);
      mTextView = (TextView) itemView.findViewById(R.id.tv);
    }
  }
}
