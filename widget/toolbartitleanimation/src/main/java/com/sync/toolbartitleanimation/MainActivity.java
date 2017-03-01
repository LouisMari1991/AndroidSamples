package com.sync.toolbartitleanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextSwitcher;

public class MainActivity extends AppCompatActivity {

  private TextSwitcher mSwitcher;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
