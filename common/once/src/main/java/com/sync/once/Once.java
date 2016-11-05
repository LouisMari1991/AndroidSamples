package com.sync.once;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;

/**
 * Created by YH on 2016-11-03.
 */

public class Once {

  public static final int THIS_APP_INSTALL = 0;
  public static final int THIS_APP_VERSION = 1;
  public static final int THIS_APP_SESSION = 2;

  private static long lastAppUpdateTime = -1;

  private static PersistedMap tagLastSeenMap;
  private static PersistedSet toDoSet;
  private static ArrayList<String> sessionList;

  private Once() {

  }

  /**
   * This method needs to be called before Once can be used.
   * Typically will be called from your Application class's onCreate method.
   *
   * @author YH
   * @time 2016-11-05 14:12
   */
  public static void initialise(Context context) {
    tagLastSeenMap = new PersistedMap(context, "TagLastSeenMap");
    toDoSet = new PersistedSet(context, "ToDoSet");

    if (sessionList == null) {
      sessionList = new ArrayList<>();
    }

    PackageManager packageManager = context.getPackageManager();
    try {
      PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
      lastAppUpdateTime = packageInfo.lastUpdateTime;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }
}
