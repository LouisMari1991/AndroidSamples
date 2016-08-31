package com.sync.androidsamples;

import android.os.Bundle;
import android.view.View;
import com.sync.androidsamples.common.BaseActivity;
import com.sync.androidsamples.common.LogUtils;
import com.sync.androidsamples.google.samples.apps.topeka.activity.SignInActivity;

public class MainActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void AndroidSamples(View v){
    LogUtils.i("AndroidSamples clicked");
    //Intent intent = new Intent(MainActivity.this, SignInActivity.class);
    //startActivity(intent);    
    SignInActivity.start(MainActivity.this, true);

    //Avatar[] avatars = Avatar.values();
    //LogUtils.i(avatars.length+"");
    //for (Avatar avatar : avatars) {
    //  LogUtils.i(avatar.getNameForAccessibility());
    //}

  }


}
