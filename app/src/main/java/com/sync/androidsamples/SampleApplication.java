package com.sync.androidsamples;

import android.app.Application;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.sync.androidsamples.common.AppBlockCanaryContext;

/**
 * Created by YH on 2016/8/19.
 */
public class SampleApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    LeakCanary.install(this);

    // Do it on main process
    BlockCanary.install(this, new AppBlockCanaryContext()).start();

  }

}
