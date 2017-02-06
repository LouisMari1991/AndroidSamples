package com.sync.topactivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Author：Administrator on 2017/2/5 0005 17:32
 * Contact：289168296@qq.com
 */
public class NotificationActionReceiver extends BroadcastReceiver {
  public static final int NOTIFICATION_ID = 1;
  public static final String ACTION_NOTIFICATION_RECEIVER = "";
  public static final int ACTION_PAUSE = 0;
  public static final int ACTION_RESUME = 1;
  public static final int ACTION_STOP = 2;
  public static final String EXTRA_NOTIFICATION_ACTION = "command";

  @Override public void onReceive(Context context, Intent intent) {
    int command = intent.getIntExtra(EXTRA_NOTIFICATION_ACTION, -1);
    switch (command) {
      case ACTION_RESUME: {
        break;
      }
      case ACTION_PAUSE: {
        break;
      }
      case ACTION_STOP: {
        break;
      }
    }
  }

  public static void showNotification(Context context, boolean isPause) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
  }

  public static PendingIntent getPendingInent(Context context, int command) {
    Intent intent = new Intent(ACTION_NOTIFICATION_RECEIVER);
    intent.putExtra(EXTRA_NOTIFICATION_ACTION, command);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, command, intent, 0);
    return pendingIntent;
  }

  public static void cancelNotification(Context context) {
    NotificationManager nm =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    nm.cancel(NOTIFICATION_ID);
  }
}
