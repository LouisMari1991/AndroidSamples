package com.sync.projectpattern.mvp.presenter;

import android.os.Handler;
import com.sync.projectpattern.mvp.bean.User;
import com.sync.projectpattern.mvp.model.IUserBiz;
import com.sync.projectpattern.mvp.model.OnLoginListener;
import com.sync.projectpattern.mvp.model.UserBiz;
import com.sync.projectpattern.mvp.view.IUserLoginView;

/**
 * Presenter是用作Model和View之间交互的桥梁，那么应该有什么方法呢？
 * 其实也是主要看该功能有什么操作，比如本例，两个操作:login和clear。
 * Author：Administrator on 2017/1/14 0014 15:58
 * Contact：289168296@qq.com
 */
public class UserLoginPresenter {
  // view
  private IUserLoginView iUserLoginView;
  // model
  private IUserBiz mUserBiz;
  private Handler mHandler = new Handler();

  public UserLoginPresenter(IUserLoginView iUserLoginView) {
    this.iUserLoginView = iUserLoginView;
    mUserBiz = new UserBiz();
  }

  public void login() {
    // view
    iUserLoginView.showLoading();
    // model
    mUserBiz.login(iUserLoginView.getUserName(), iUserLoginView.getPassword(),
        new OnLoginListener() {
          @Override public void loginSuccess(User user) {
            mHandler.post(new Runnable() {
              @Override public void run() {
                iUserLoginView.toMainActivity();
                iUserLoginView.hindLoading();
              }
            });
          }

          @Override public void loginFailed() {
            iUserLoginView.hindLoading();
            iUserLoginView.showFailedError();
          }
        });
  }

  public void clear() {
    iUserLoginView.clearUserName();
    iUserLoginView.clearPassword();
  }
}
