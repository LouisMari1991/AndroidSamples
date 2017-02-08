package com.sync.materialdesign.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sync.materialdesign.R;
import com.sync.materialdesign.main.adapter.MyRecyclerViewAdapter;
import com.sync.materialdesign.main.adapter.MyStaggeredViewAdapter;
import com.sync.materialdesign.main.utils.SnackbarUtil;

/**
 * Author：Administrator on 2017/1/15 0015 14:43
 * Contact：289168296@qq.com
 */
public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

  public static MyFragment newInstance(Bundle b) {
    MyFragment fragment = new MyFragment();
    fragment.setArguments(b);
    return fragment;
  }

  private SwipeRefreshLayout mSwipeRefreshLayout;
  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private MyRecyclerViewAdapter mRecyclerViewAdapter;
  private MyStaggeredViewAdapter mStaggeredAdapter;

  private static final int VERTICAL_LIST = 0;
  private static final int HORIZONTAL_LIST = 1;
  private static final int VERTICAL_GRID = 2;
  private static final int HORIZONTAL_GRID = 3;
  private static final int STAGGERED_GRID = 4;

  private static final int SPAN_COUNT = 2;
  private int flag = 0;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, container, false);
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
    mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
    flag = (int) getArguments().get("flag");
    configRecyclerView();

    // 刷新时，指示器旋转后变化的颜色
    mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark);
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void configRecyclerView() {
    switch (flag) {
      case VERTICAL_LIST:
        mLayoutManager =
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
      case HORIZONTAL_LIST:
        mLayoutManager =
            new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        break;
      case VERTICAL_GRID:
        mLayoutManager =
            new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
        break;
      case HORIZONTAL_GRID:
        mLayoutManager =
            new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
        break;
      case STAGGERED_GRID:
        mLayoutManager =
            new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        break;
      default:
        mLayoutManager =
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
    }

    if (flag != STAGGERED_GRID) {
      mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity());
      mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
        @Override public void onItemClick(View v, int position) {
          SnackbarUtil.show(mRecyclerView, getString(R.string.item_clicked), 0);
        }

        @Override public void onItemLongClick(View v, int position) {
          SnackbarUtil.show(mRecyclerView, getString(R.string.item_longclicked), 0);
        }
      });
      mRecyclerView.setAdapter(mRecyclerViewAdapter);
    } else {
      mStaggeredAdapter = new MyStaggeredViewAdapter(getActivity());
      mStaggeredAdapter.setmOnItemClickListener(new MyStaggeredViewAdapter.OnItemClickListener() {
        @Override public void onItemClick(View v, int position) {
          SnackbarUtil.show(mRecyclerView, getString(R.string.item_clicked), 0);
        }

        @Override public void onItemLongClick(View v, int position) {
          SnackbarUtil.show(mRecyclerView, getString(R.string.item_longclicked), 0);
        }
      });
      mRecyclerView.setAdapter(mStaggeredAdapter);
    }
    mRecyclerView.setLayoutManager(mLayoutManager);
  }

  public void onRefresh() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
        int temp = (int) (Math.random() * 10);
        if (flag != STAGGERED_GRID) {
          mRecyclerViewAdapter.mDatas.add(0, "new" + temp);
          mRecyclerViewAdapter.notifyDataSetChanged();
        } else {
          mStaggeredAdapter.mDatas.add(0, "new" + temp);
          mStaggeredAdapter.mHeights.add(0, (int) (Math.random() * 300) + 200);
          mStaggeredAdapter.notifyDataSetChanged();
        }
      }
    }, 2000);
  }
}
