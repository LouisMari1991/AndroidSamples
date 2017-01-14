package com.sync.projectpattern.mvp.model;

import com.sync.projectpattern.mvp.bean.User;

/**
 * Author：Administrator on 2017/1/14 0014 15:41
 * Contact：289168296@qq.com
 */
public interface OnLoginListener {
  void loginSuccess(User user);
  void loginFailed();
}
