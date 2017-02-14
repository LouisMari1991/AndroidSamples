package com.sync.webviewsample.config;

/**
 * Created by YH on 2017-02-14.
 */

public interface IWebPageView {

  /**
   * 隐藏进度条
   */
  void hideProgressBar();

  /**
   * 显示webview
   */
  void showWebView();

  /**
   * 隐藏webview
   */
  void hideWebView();

  /**
   * 进度条先加载到90%,然后再加载到100%
   */
  void startProgress();

  /**
   * 进度条变化时调用
   */
  void progressChanged(int newProgress);

  /**
   * 添加js监听
   */
  void addImageClickListener();

  /**
   * 播放网络视频全屏调用
   */
  void fullViewAddView();

  void showVideoFullView();

  void hideVideoFullView();
}
