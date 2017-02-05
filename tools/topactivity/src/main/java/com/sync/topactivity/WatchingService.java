package com.sync.topactivity;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.Timer;

/**
 * Author：Administrator on 2017/2/5 0005 17:29
 * Contact：289168296@qq.com
 */
public class WatchingService extends Service {
  private Handler mHandler = new Handler();
  private ActivityManager mActivityManager;
  private String text = null;
  private Timer timer;
  private NotificationManager mManager;
  private final int NOTIF_ID = 1;

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }
}
