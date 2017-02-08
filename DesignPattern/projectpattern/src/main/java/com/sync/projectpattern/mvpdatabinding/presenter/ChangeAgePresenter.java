package com.sync.projectpattern.mvpdatabinding.presenter;

import android.view.View;
import com.sync.projectpattern.mvvm.bean.User;
import com.sync.projectpattern.mvvm.model.UserModel;

/**
 * Author：Administrator on 2017/1/14 0014 16:58
 * Contact：289168296@qq.com
 */
public class ChangeAgePresenter {
  /**
   * 在Activity使用
   */
  public void changeAge(User myUser) {
    UserModel userModel1 = new UserModel();
    userModel1.changeEssay(myUser, 2);
  }

  /**
   * 布局文件直接使用
   */
  public static void itemClick(View view, final User mUser) {
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        UserModel userModel1 = new UserModel();
        userModel1.changeEssay(mUser, 2);
      }
    });
  }
}
