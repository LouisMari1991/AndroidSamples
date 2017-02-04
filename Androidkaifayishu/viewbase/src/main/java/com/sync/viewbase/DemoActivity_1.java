package com.sync.viewbase;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import com.sync.viewbase.databinding.ActivityDemo1Binding;

/**
 * Created by YH on 2017-02-04.
 */

public class DemoActivity_1 extends AppCompatActivity {

  private ActivityDemo1Binding mBinding;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo_1);

  }
}
