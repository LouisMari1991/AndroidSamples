package com.sync.projectpattern.mvpdatabinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sync.projectpattern.R;
import com.sync.projectpattern.databinding.ActivityMvpDataBindingBinding;
import com.sync.projectpattern.mvpdatabinding.presenter.ChangeAgePresenter;
import com.sync.projectpattern.mvvm.bean.User;

/**
 * Author：Administrator on 2017/1/14 0014 16:16
 * Contact：289168296@qq.com
 */
public class MvpDataBindingActivity extends AppCompatActivity {
  private ActivityMvpDataBindingBinding viewDataBinding;
  private User myUser;
  private ChangeAgePresenter changeAgePresenter = new ChangeAgePresenter();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mvp_data_binding);
    setTitle("MVP + data-binding");
    viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvp_data_binding);
    myUser = new User("年龄", 23);
    viewDataBinding.setUser(myUser);
    viewDataBinding.setButtonname("年龄+2");
  }
}

