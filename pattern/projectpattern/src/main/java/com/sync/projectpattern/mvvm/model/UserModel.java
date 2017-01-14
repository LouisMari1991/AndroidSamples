package com.sync.projectpattern.mvvm.model;

import com.sync.projectpattern.mvvm.bean.User;

/**
 * Author：Administrator on 2017/1/14 0014 17:15
 * Contact：289168296@qq.com
 */
public class UserModel {

  private Chage change;

  public interface Chage {
    void success(User user);
  }

  /**
   * User 没有继承BaseObservable时
   */
  public void changeAge(User user, int add, Chage chage) {

    // 一系列操作
    user.age = user.age + add;

    chage.success(user);
  }

  /**
   * 继承后直接更新UI
   */
  public void changeEssay(User user, int add) {
    //        user.setAge(user.getAge() + add);
    user.setAge(user.age + add);
  }
}
