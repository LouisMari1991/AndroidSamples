package com.sync.viewbase;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sync.viewbase.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding mBinding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
  }

  public void onButtonClick(View v) {
    Intent intent = new Intent();
    if (v.getId() == R.id.button1) {
      intent.setClass(this, TestActivity.class);
    } else if (v.getId() == R.id.button2) {
      intent.setClass(this, DemoActivity_1.class);
    } else if (v.getId() == R.id.button3) {
      intent.setClass(this, DemoActivity_2.class);
    }
    startActivity(intent);
  }
}
