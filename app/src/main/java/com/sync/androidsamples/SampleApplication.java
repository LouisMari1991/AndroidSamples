package com.sync.androidsamples;

import android.app.Application;
import android.content.Context;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.sync.androidsamples.common.AppBlockCanaryContext;

/**
 * Created by YH on 2016/8/19.
 */
public class SampleApplication extends Application {


  public static RefWatcher getRefWatcher(Context context) {
    SampleApplication application = (SampleApplication) context.getApplicationContext();
    return application.refWatcher;
  }

  private RefWatcher refWatcher;

  @Override public void onCreate() {
    super.onCreate();
    refWatcher = LeakCanary.install(this);

    // Do it on main process
    BlockCanary.install(this, new AppBlockCanaryContext()).start();

  }

}
