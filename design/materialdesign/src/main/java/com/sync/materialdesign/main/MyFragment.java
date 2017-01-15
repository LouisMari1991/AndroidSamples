package com.sync.materialdesign.main;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Author：Administrator on 2017/1/15 0015 14:43
 * Contact：289168296@qq.com
 */
public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

  private SwipeRefreshLayout mSwipeRefreshLayout;
  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;


  public void onRefresh() {

  }
}
