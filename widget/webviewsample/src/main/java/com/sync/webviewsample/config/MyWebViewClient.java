package com.sync.webviewsample.config;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.sync.webviewsample.WebViewActivity;

/**
 * Author：Administrator on 2017/2/15 0015 22:29
 * Contact：289168296@qq.com
 */
public class MyWebViewClient extends WebViewClient {

  private IWebPageView mIWebPageView;
  private WebViewActivity mActivity;

  public MyWebViewClient(IWebPageView iWebPageView) {
    this.mIWebPageView = iWebPageView;
    mActivity = (WebViewActivity) mIWebPageView;
  }

  @SuppressWarnings("deprecation") @Override
  public boolean shouldOverrideUrlLoading(WebView view, String url) {
    // 优酷视屏跳转浏览器播放
    if (url.startsWith("http://v.youku.com/")) {

    }
    return super.shouldOverrideUrlLoading(view, url);
  }
}
