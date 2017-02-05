package com.sync.topactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

  CompoundButton mWindowSwitch;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mWindowSwitch = (CompoundButton) findViewById(R.id.sw_window);

  }
}
