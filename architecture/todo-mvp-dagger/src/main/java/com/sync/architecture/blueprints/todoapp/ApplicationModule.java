package com.sync.architecture.blueprints.todoapp;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author：SYNC on 2017/6/1 0001 22:40
 * Contact：289168296@qq.com
 */
@Module public final class ApplicationModule {

  private final Context mContext;

  ApplicationModule(Context context) {
    this.mContext = context;
  }

  @Provides Context provideContext() {
    return mContext;
  }
}
