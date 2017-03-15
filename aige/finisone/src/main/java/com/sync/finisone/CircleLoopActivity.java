package com.sync.finisone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sync.finisone.widget.CircleLoopView;

/**
 * Created by YH on 2017-03-15.
 */

public class CircleLoopActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_circle_loop);
    CircleLoopView view = (CircleLoopView) findViewById(R.id.view);
    new Thread(view).start();
  }
}
