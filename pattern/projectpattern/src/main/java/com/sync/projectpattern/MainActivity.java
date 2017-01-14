package com.sync.projectpattern;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.sync.projectpattern.mvc.LoadDataActivity;
import com.sync.projectpattern.mvp.UserLoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.bt_mvc).setOnClickListener(this);
    findViewById(R.id.bt_mvp).setOnClickListener(this);
    findViewById(R.id.bt_mvp_data_binding).setOnClickListener(this);
    findViewById(R.id.bt_mvvm_data_binding).setOnClickListener(this);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.bt_mvc:
        startActivity(new Intent(MainActivity.this, LoadDataActivity.class));
        break;
      case R.id.bt_mvp:
        startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
        break;
      case R.id.bt_mvp_data_binding:

        break;
      case R.id.bt_mvvm_data_binding:

        break;
    }
  }
}
