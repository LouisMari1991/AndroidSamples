package com.sync.coordinatorappbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.sync.coordinatorappbar.adapter.CommonRecyclerAdapter;
import java.util.List;

/**
 * Description:
 * Author：Mari on 2017-08-10 22:38
 * Contact：289168296@qq.com
 */
public class CoordinatorToolbarActivity extends AppCompatActivity{

  private RecyclerView mRecyclerView;
  private CommonRecyclerAdapter<String> mAdapter;
  private List<String> mStringList;
  private Toolbar mToolbar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }
}
