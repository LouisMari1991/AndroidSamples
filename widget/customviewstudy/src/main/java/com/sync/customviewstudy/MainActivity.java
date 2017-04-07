package com.sync.customviewstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.sync.customviewstudy.activity.CustomImageViewActivity;
import com.sync.customviewstudy.activity.CustomImgContainerActivity;
import com.sync.customviewstudy.activity.CustomProgressBarActivity;
import com.sync.customviewstudy.activity.CustomTitleViewActivity;
import com.sync.customviewstudy.activity.CustomVolumeControlBarActivity;

public class MainActivity extends AppCompatActivity implements OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.bt_custom_view_01).setOnClickListener(this);
    findViewById(R.id.bt_custom_view_02).setOnClickListener(this);
    findViewById(R.id.bt_custom_view_03).setOnClickListener(this);
    findViewById(R.id.bt_custom_view_04).setOnClickListener(this);
    findViewById(R.id.bt_custom_view_05).setOnClickListener(this);
    findViewById(R.id.bt_custom_view_06).setOnClickListener(this);
    findViewById(R.id.bt_custom_view_07).setOnClickListener(this);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.bt_custom_view_01:
        startActivity(new Intent(view.getContext(), CustomTitleViewActivity.class));
        break;
      case R.id.bt_custom_view_02:
        startActivity(new Intent(view.getContext(), CustomImageViewActivity.class));
        break;
      case R.id.bt_custom_view_03:
        startActivity(new Intent(view.getContext(), CustomProgressBarActivity.class));
        break;
      case R.id.bt_custom_view_04:
        startActivity(new Intent(view.getContext(), CustomVolumeControlBarActivity.class));
        break;
      case R.id.bt_custom_view_05:
        startActivity(new Intent(view.getContext(), CustomImgContainerActivity.class));
        break;
      case R.id.bt_custom_view_06:
        break;
      case R.id.bt_custom_view_07:
        break;
    }
  }
}
