package com.sync.customviewstudy.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.sync.customviewstudy.R;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月07日 14:14
 */
public class DeepUnderstandAttrActivity extends AppCompatActivity {

  private WebView     mWebView;
  private ProgressBar mProgressBar; // 进度条

  private boolean progress90;
  private boolean pageFinish;

  public ValueCallback<Uri>   nUploadMessage;
  public ValueCallback<Uri[]> mUploadMessageForAndroid5;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("深入理解Android中的自定义属性");
    setContentView(R.layout.activity_deep_understand_attr);
    initView();
    initWebView();
  }

  private void initView() {
    mWebView = (WebView) findViewById(R.id.activity_deep_understand_att);
    mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
    mProgressBar.setVisibility(View.VISIBLE);
  }

  private void initWebView() {
    WebSettings settings = mWebView.getSettings();
    settings.setLoadWithOverviewMode(true); // setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。

  }

  public class MyWebViewClient extends WebViewClient {

  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (mWebView.canGoBack()) {
        mWebView.goBack();
        return true;
      } else {
        mWebView.loadUrl("about:blank");
        finish();
      }
    }
    return false;
  }
}
