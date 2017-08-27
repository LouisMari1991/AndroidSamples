package com.sync.coordinatorappbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void btnClick1(View v) {
    startActivity(new Intent(this, CoordinatorActivity.class));
  }

  public void btnClick2(View v) {
    startActivity(new Intent(this, CoordinatorAppbarActivity.class));
  }

  public void btnClick3(View v) {
    startActivity(new Intent(this, CoordinatorToolbarActivity.class));
  }

  public void btnClick4(View v) {
    startActivity(new Intent(this, CoordinatorLayoutTestActivity.class));
  }
}
