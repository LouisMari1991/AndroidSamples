package com.sync.base.helper;

import android.view.View;

/**
 * Created by YH on 2017-01-16.
 */

public abstract class PerfectClickListener implements View.OnClickListener {

  public static final int MIN_CLICK_DELAY_TIME = 1000;
  private long lastClickTime = 0;
  private int id = -1;

  @Override public void onClick(View v) {
    long currentTime = System.currentTimeMillis();
    int viewId = v.getId();
    if (id != viewId) {
      onNoDoubleClick(v);
      id = viewId;
      lastClickTime = currentTime;
    }
    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
      lastClickTime = currentTime;
      onNoDoubleClick(v);
    }
  }

  protected abstract void onNoDoubleClick(View v);
}
