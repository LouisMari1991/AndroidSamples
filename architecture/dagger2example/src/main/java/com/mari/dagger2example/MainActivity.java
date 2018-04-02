package com.mari.dagger2example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.sync.logger.Logger;
import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends AppCompatActivity {

  @Dev
  @Inject ApiService apiServiceDev;

  @Release
  @Inject ApiService apiServiceRelease;

  @Inject UserStore userStore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    DaggerUserComponent.builder().userModule(new UserModule(this)).build().inject(this);

    Logger.i(userStore.toString());
    Logger.i(apiServiceDev.toString());
    Logger.i(apiServiceRelease.toString());
  }
}
