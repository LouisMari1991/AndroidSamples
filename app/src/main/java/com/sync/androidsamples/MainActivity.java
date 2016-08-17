package com.sync.androidsamples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.sync.androidsamples.common.BaseActivity;
import com.sync.androidsamples.googlesamples.topeka.topeka.activity.SignInActivity;

public class MainActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void AndroidSamples(View v){
    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
    startActivity(intent);
  }

}
