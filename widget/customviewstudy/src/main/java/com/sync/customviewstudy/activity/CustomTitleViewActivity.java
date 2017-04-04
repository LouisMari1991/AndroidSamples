package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sync.customviewstudy.R;

/**
 * Description:
 * Author：SYNC on 2017/4/4 0004 18:35
 * Contact：289168296@qq.com
 */
public class CustomTitleViewActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_title_view);
    setTitle("自定义View (一)");
  }
}
