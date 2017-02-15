package com.sync.webviewsample.config;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * Author：Administrator on 2017/2/15 0015 22:30
 * Contact：289168296@qq.com
 */
public class MyWebChromeClient extends WebChromeClient {

  private ValueCallback<Uri> mUploadMessage;
  private ValueCallback<Uri[]> mUploadMessageForAndroid5;



}
