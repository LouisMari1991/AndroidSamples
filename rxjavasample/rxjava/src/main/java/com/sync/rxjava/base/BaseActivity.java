package com.sync.rxjava.base;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by YH on 2017-01-19.
 */

public abstract class BaseActivity<SV extends ViewDataBinding> extends AppCompatActivity {

  protected SV binding;

  //private Compo



  protected abstract int getContent();

}
