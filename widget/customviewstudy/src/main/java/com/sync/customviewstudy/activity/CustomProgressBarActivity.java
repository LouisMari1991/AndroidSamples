package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sync.customviewstudy.R;
import com.sync.customviewstudy.view.CustomProgressBar;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月06日 15:12
 */
public class CustomProgressBarActivity extends AppCompatActivity {

  private CustomProgressBar customProgressBar01;
  private CustomProgressBar customProgressBar02;
  private CustomProgressBar customProgressBar03;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_progress_bar);
    setTitle("自定义View (三) 圆环交替 等待效果");

    customProgressBar01 = (CustomProgressBar) findViewById(R.id.custom_progress_bar_01);
    customProgressBar02 = (CustomProgressBar) findViewById(R.id.custom_progress_bar_02);
    customProgressBar03 = (CustomProgressBar) findViewById(R.id.custom_progress_bar_03);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    customProgressBar01.setContinue(false);
    customProgressBar02.setContinue(false);
    customProgressBar03.setContinue(false);
    customProgressBar01 = null;
    customProgressBar02 = null;
    customProgressBar03 = null;
  }
}
