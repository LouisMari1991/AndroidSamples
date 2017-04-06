package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sync.customviewstudy.R;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月06日 11:09
 */
public class CustomImageViewActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_image_view);
    setTitle("自定义View (二) 进阶");
  }
}
