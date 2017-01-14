package com.sync.projectpattern.mvp.model;

import android.os.Handler;
import com.sync.projectpattern.mvp.bean.User;

/**
 * Author：Administrator on 2017/1/14 0014 15:45
 * Contact：289168296@qq.com
 */
public class UserBiz implements IUserBiz {
  @Override public void login(final String username, final String password,
      final OnLoginListener loginListener) {
    // 模拟子线程耗时操作
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        if ("sync".equals(username) && "110".equals(password)) {
          User user = new User();
          user.setUsername(username);
          user.setPassword(password);
          loginListener.loginSuccess(user);
        } else {
          loginListener.loginFailed();
        }
      }
    }, 2000);
  }
}
