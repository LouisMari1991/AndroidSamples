package com.mari.dagger2example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.sync.logger.Logger;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @Inject
  UserManager userManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    DaggerUserComponent.builder().userModule(new UserModule(this)).build().inject(this);
    userManager.register();
    Logger.i(userManager.toString());
  }
}
