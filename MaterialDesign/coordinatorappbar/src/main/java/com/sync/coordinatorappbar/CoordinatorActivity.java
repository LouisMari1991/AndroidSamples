package com.sync.coordinatorappbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description:
 * Author：Mari on 2017-08-07 22:59
 * Contact：289168296@qq.com
 */
public class CoordinatorActivity extends AppCompatActivity {

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator);
    findViewById(R.id.btn).setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
          v.setX(event.getRawX() - v.getWidth() / 2);
          v.setY(event.getRawY() - v.getHeight() / 2);
        }
        return true;
      }
    });
  }
}
