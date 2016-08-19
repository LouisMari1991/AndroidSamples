package com.sync.androidsamples.common;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.sync.androidsamples.BuildConfig;

/**
 * Created by YH on 2016/8/19.
 */
public class AppBlockCanaryContext extends BlockCanaryContext {

  // override to provide context like app qualifier, uid, network type, block threshold, log save path

  // this is default block threshold, you can set it by phone's performance
  @Override
  public int getConfigBlockThreshold() {
    return 500;
  }

  // if set true, notification will be shown, else only write log file
  @Override
  public boolean isNeedDisplay() {
    return BuildConfig.DEBUG;
  }

  // path to save log file
  @Override
  public String getLogPath() {
    return "/blockCanary/performance";
  }

}
