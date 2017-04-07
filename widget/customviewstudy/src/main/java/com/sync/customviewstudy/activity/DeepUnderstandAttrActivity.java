package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.sync.customviewstudy.R;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月07日 14:14
 */
public class DeepUnderstandAttrActivity extends AppCompatActivity {

  private WebView     mWebView;
  private ProgressBar mProgressBar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_deep_understand_attr);
  }
}
