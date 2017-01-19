package com.sync.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by YH on 2017-01-19.
 */

public abstract class BaseActivity<SV extends ViewDataBinding> extends AppCompatActivity {

  protected SV bindingView;

  //private ActivityBaseBinding mBaseBinding;

  @Override public void setContentView(@LayoutRes int layoutResID) {
    bindingView = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);

  }

  protected <T extends View> T getView(@IdRes int id) {
    return (T) findViewById(id);
  }
}
