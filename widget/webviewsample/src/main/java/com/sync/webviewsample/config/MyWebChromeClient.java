package com.sync.webviewsample.config;

import android.net.Uri;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.sync.webviewsample.WebViewActivity;

/**
 * Author：Administrator on 2017/2/15 0015 22:30
 * Contact：289168296@qq.com
 */
public class MyWebChromeClient extends WebChromeClient {

  private ValueCallback<Uri> mUploadMessage;
  private ValueCallback<Uri[]> mUploadMessageForAndroid5;

  public static int FILECHOOSE_RESULT_CODE = 1;
  public static int FILECHOOSE_RESULT_CODE_FOR_ANDROID_5 = 2;

  private View mXProgressVideo;
  private WebViewActivity mActivity;
  private IWebPageView mIWebPageView;
  private View mXCustomView;
  private CustomViewCallback mXCustomViewCallback;

  public MyWebChromeClient(IWebPageView iWebPageView) {
    mIWebPageView = iWebPageView;
  }

  @Override public void onShowCustomView(View view, CustomViewCallback callback) {
    super.onShowCustomView(view, callback);
  }

  @Override public void onHideCustomView() {
    super.onHideCustomView();
  }

  @Override public View getVideoLoadingProgressView() {
    return super.getVideoLoadingProgressView();
  }

  @Override public void onProgressChanged(WebView view, int newProgress) {
    super.onProgressChanged(view, newProgress);
  }



}
