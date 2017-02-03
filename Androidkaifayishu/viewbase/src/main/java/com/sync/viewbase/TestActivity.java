package com.sync.viewbase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by YH on 2017-02-03.
 */

public class TestActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  private Handler mHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
    }
  };

  @Override public void onClick(View v) {

  }

  @Override public boolean onLongClick(View v) {
    return false;
  }
}
