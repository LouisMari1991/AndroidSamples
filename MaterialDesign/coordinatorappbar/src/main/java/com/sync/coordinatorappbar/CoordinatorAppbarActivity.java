package com.sync.coordinatorappbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.sync.coordinatorappbar.adapter.CommonRecyclerAdapter;
import com.sync.coordinatorappbar.adapter.CommonRecyclerHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author：Mari on 2017-08-08 20:14
 * Contact：289168296@qq.com
 */
public class CoordinatorAppbarActivity extends AppCompatActivity {

  private RecyclerView                  mRecyclerView;
  private CommonRecyclerAdapter<String> mAdapter;
  private List<String>                  mStringList;
  private Toolbar                       mToolbar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator_app_bar);

    mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
    //mToolbar = (Toolbar) findViewById(R.id.toolbar);
    //mToolbar.setLogo(getResources().getDrawable(R.mipmap.ic_launcher));
    //mToolbar.setTitle("这是标题");
    //mToolbar.inflateMenu(R.menu.menu);

    mStringList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      mStringList.add("测试 : " + i);
    }

    mAdapter = new CommonRecyclerAdapter<String>(this, mStringList, R.layout.layout_item) {
      @Override public void convert(CommonRecyclerHolder holder, String item, int position, boolean isScrolling) {
        holder.setText(R.id.item_text, mStringList.get(position));
      }
    };

    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
  }
}
