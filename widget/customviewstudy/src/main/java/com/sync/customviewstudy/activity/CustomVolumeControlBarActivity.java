package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sync.customviewstudy.R;
import com.sync.logger.Logger;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月06日 18:08
 */
public class CustomVolumeControlBarActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_volume_control_bar);
    setTitle("TestView");
    findViewById(R.id.test_view).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Logger.i(" click ");
      }
    });
    findViewById(R.id.test_view).setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        Logger.i(" long click ");
        return true;
      }
    });
  }
}
