package com.sync.topactivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

/**
 * Author：Administrator on 2017/2/5 0005 21:53
 * Contact：289168296@qq.com
 */
public class WatchingAccessibilityService extends AccessibilityService {
  private static WatchingAccessibilityService sInstance;

  @Override public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    if (SPHelper.isShowWindow(this)) {
      TaskWindow.show(this,
          accessibilityEvent.getPackageName() + "\n" + accessibilityEvent.getClassName());
    }
  }

  @Override public void onInterrupt() {

  }

  @Override protected void onServiceConnected() {
    super.onServiceConnected();
  }

  @Override public boolean onUnbind(Intent intent) {
    sInstance = null;
    TaskWindow.dismiss(this);
    //NotificationActionReceiver
    return super.onUnbind(intent);
  }

  public static WatchingAccessibilityService getInstance() {
    return sInstance;
  }
}
