package com.sync.projectpattern.mvvm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sync.projectpattern.R;
import com.sync.projectpattern.databinding.ActivityChangeAgeBinding;
import com.sync.projectpattern.mvvm.bean.User;
import com.sync.projectpattern.mvvm.model.UserModel;

/**
 * Author：Administrator on 2017/1/14 0014 17:39
 * Contact：289168296@qq.com
 */
public class ChangeAgeActivity extends AppCompatActivity {
  private User myUser;
  private ActivityChangeAgeBinding viewDataBinding;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("MVVM + data-binding");
    viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_age);
    myUser = new User("年龄", 23);
    viewDataBinding.setUser(myUser);
    viewDataBinding.btAge.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        /**
         * User 继承BaseObservable后，实现自动同步，无需viewDataBinding.setUser(user);
         */
        UserModel userModel = new UserModel();
        userModel.changeEssay(myUser, 2);
      }
    });
  }
}
