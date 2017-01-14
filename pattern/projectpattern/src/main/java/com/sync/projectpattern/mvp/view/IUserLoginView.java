package com.sync.projectpattern.mvp.view;

/**
 * 总结下，对于View的接口，去观察功能上的操作，然后考虑：
 * 该操作需要什么？（getUserName, getPassword）
 * 该操作的结果，对应的反馈？(toMainActivity, showFailedError)
 * 该操作过程中对应的友好的交互？(showLoading, hideLoading)
 */
public interface IUserLoginView {
  String getUserName();

  String getPassword();

  void clearUserName();

  void clearPassword();

  void showLoading();

  void hindLoading();

  void toMainActivity();

  void showFailedError();
}
