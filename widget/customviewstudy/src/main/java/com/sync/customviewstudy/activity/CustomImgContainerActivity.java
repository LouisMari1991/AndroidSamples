package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sync.customviewstudy.R;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月07日 13:56
 */
public class CustomImgContainerActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_img_container);
    setTitle("手把手教您自定义ViewGroup(一)");
  }
}
