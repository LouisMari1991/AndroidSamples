package com.sync.projectpattern.mvp.model;

/**
 * Author：Administrator on 2017/1/14 0014 15:40
 * Contact：289168296@qq.com
 */
public interface IUserBiz {
  public void login(String username, String password, OnLoginListener loginListener);
}
