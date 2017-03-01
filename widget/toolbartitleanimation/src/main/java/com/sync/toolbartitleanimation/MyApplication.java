package com.sync.toolbartitleanimation;

import android.app.Application;
import com.sync.toolbartitleanimation.font.FontsOverride;

/**
 * Author：Administrator on 2017/3/1 0001 20:50
 * Contact：289168296@qq.com
 */
public class MyApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    /**
     * Apply custom font to entire app
     */
    FontsOverride.setDefaultFont(this, "DEFAULT", "roboto_condensed.ttf");
    FontsOverride.setDefaultFont(this, "MONOSPACE", "roboto_condensed.ttf");
    FontsOverride.setDefaultFont(this, "SERIF", "roboto_condensed.ttf");
    FontsOverride.setDefaultFont(this, "SANS_SERIF", "roboto_condensed.ttf");
  }
}
