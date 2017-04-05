package com.sync.dragrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;
import com.sync.dragrecyclerview.drag.DragItemCallBack;
import com.sync.dragrecyclerview.drag.RecycleCallBack;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements RecycleCallBack {

  private RecyclerView      mRecyclerView;
  private DragAdapter       mDragAdapter;
  private ArrayList<String> mList;
  private ItemTouchHelper   mItemTouchHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      mList.add(String.valueOf(i));
    }
    mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    mDragAdapter = new DragAdapter(this, mList);
    mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
    mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    mRecyclerView.setAdapter(mDragAdapter);
  }

  @Override public void onItemClick(int position, View view) {
    if (view.getId() == R.id.del) {
      mList.remove(position);
      mDragAdapter.setData(mList);
      mDragAdapter.notifyItemRemoved(position);
    } else {
      Toast.makeText(MainActivity.this, "当前点击的是" + position, Toast.LENGTH_SHORT).show();
      mDragAdapter.notifyDataSetChanged();
    }
  }

  @Override public void onItemMove(int from, int to) {
    synchronized (this) {
      if (from > to) {
        int count = from - to;
        for (int i = 0; i < count; i++) {
          Collections.swap(mList, from - i, from - i - 1);
        }
      }
      if (from < to) {
        int count = to - from;
        for (int i = 0; i < count; i++) {
          Collections.swap(mList, from + 1, from + 1 + 1);
        }
      }
    }
    mDragAdapter.setData(mList);
    mDragAdapter.notifyItemMoved(from, to);
    mDragAdapter.show.clear();
    mDragAdapter.show.put(to, to);
  }
}
