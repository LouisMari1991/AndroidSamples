package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sync.customviewstudy.R;

/**
 * Description:
 * Author：SYNC on 2017/4/30 0030 17:10
 * Contact：289168296@qq.com
 */
public class VDHLayoutActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vdhlayout);
    setTitle("ViewDragHelper完全解析(基础)");
  }
}
