package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.sync.customviewstudy.R;
import com.sync.customviewstudy.viewgroup.FlowLayout;

/**
 * Description:
 * Author：Mari on 2017-07-30 19:23
 * Contact：289168296@qq.com
 */
public class FlowLayoutActivity extends AppCompatActivity {

  FlowLayout flowLayout;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flow_layout);

    flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
    initData();
  }

  void initData() {

    LayoutInflater inflater = LayoutInflater.from(this);
    for (int i = 0; i < 50; i++) {
      TextView tv = (TextView) inflater.inflate(R.layout.tv, flowLayout, false);
      flowLayout.addView(tv);
    }
  }
}
