package com.sync.topactivity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends AppCompatActivity implements OnCheckedChangeListener {

  CompoundButton mWindowSwitch;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mWindowSwitch = (CompoundButton) findViewById(R.id.sw_window);
    mWindowSwitch.setOnCheckedChangeListener(this);
    if (getResources().getBoolean(R.bool.use_watching_service)) {
      startService(new Intent(this, WatchingService.class));
    }
  }

  @Override protected void onResume() {
    super.onResume();
    resetUI();
    NotificationActionReceiver.cancelNotification(this);
  }

  @Override protected void onPause() {
    super.onPause();
    if (SPHelper.isShowWindow(this) && !getResources().getBoolean(R.bool.use_accessibility_service)) {
      NotificationActionReceiver.showNotification(this, false);
    }
  }

  private void resetUI() {
    mWindowSwitch.setChecked(SPHelper.isShowWindow(this));
    if (getResources().getBoolean(R.bool.use_accessibility_service)) {
      if (WatchingAccessibilityService.getInstance() == null) {
        mWindowSwitch.setChecked(false);
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
    if (isChecked && compoundButton == mWindowSwitch && getResources().getBoolean(R.bool.use_accessibility_service)) {
      if (WatchingAccessibilityService.getInstance() == null) {
        new AlertDialog.Builder(this).setMessage(R.string.dialog_enable_accessibility_msg)
            .setPositiveButton(R.string.dialog_enable_accessibility_positive_btn,
                new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setAction("android.setting.ACCESSIBILITY_SETTINGS");
                    startActivity(intent);
                  }
                })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialogInterface, int i) {
                resetUI();
              }
            })
            .create()
            .show();
        SPHelper.setIsShowWindow(this, isChecked);
        return;
      }
    }
    if (compoundButton == mWindowSwitch) {
      SPHelper.setIsShowWindow(this, isChecked);
      if (!isChecked) {
        TasksWindow.dismiss(this);
      } else {
        TasksWindow.show(this, getPackageName() + "\n" + getClass().getName());
      }
    }

  }
}
