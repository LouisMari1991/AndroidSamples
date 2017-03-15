package com.sync.finistwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.btn_cus).setOnClickListener(this);
    findViewById(R.id.btn_poter).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_cus: {
        startActivity(new Intent(MainActivity.this, CusActivity.class));
        break;
      }
      case R.id.btn_poter: {
        startActivity(new Intent(MainActivity.this, PorterDuffActivity.class));
        break;
      }
    }
  }
}
