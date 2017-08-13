package com.sync.scrollshapeui.app;

import android.app.Application;

/**
 * Description:
 * Author：Mari on 2017-07-31 22:34
 * Contact：289168296@qq.com
 */
public class MApp extends Application {

  static MApp mApplication;

  public static MApp get() {
    return mApplication;
  }

  @Override public void onCreate() {
    super.onCreate();
    mApplication = this;
  }
}
