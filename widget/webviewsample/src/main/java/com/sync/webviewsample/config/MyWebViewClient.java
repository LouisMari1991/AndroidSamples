package com.sync.webviewsample.config;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.sync.webviewsample.WebViewActivity;
import com.sync.webviewsample.utils.CheckNetwork;

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
      Intent intent = new Intent();
      intent.setAction("android.intent.action.VIEW");
      intent.addCategory("android.intent.category.DEFAULT");
      intent.addCategory("android.intent.category.BROWSABLE");
      Uri content_url = Uri.parse(url);
      mActivity.startActivity(intent);
      return true;
    } else if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith("sms:") || url.startsWith(
        WebView.SCHEME_MAILTO)) {
      // 电话、短信、邮箱
      try {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mActivity.startActivity(intent);
      } catch (ActivityNotFoundException ignored) {

      }
      return true;
    }
    mIWebPageView.startProgress();
    view.loadUrl(url);
    return false;
  }

  @Override public void onPageFinished(WebView view, String url) {
    if (mActivity.mProgress90) {
      mIWebPageView.hideProgressBar();
    } else {
      mActivity.mPageFinish = true;
    }
    if (!CheckNetwork.isNetworkConnected(mActivity)) {
      mIWebPageView.hideProgressBar();
    }
    // html加载完成以后，添加监听的点击js函数
    mIWebPageView.addImageClickListener();
    super.onPageFinished(view, url);
  }

  // 视频全屏播放返回页面被放大的问题
  @Override public void onScaleChanged(WebView view, float oldScale, float newScale) {
    super.onScaleChanged(view, oldScale, newScale);
    if (newScale - oldScale > 7) {
      // 异常放大，缩回去
      view.setInitialScale((int) (oldScale / newScale * 100));
    }
  }
}
