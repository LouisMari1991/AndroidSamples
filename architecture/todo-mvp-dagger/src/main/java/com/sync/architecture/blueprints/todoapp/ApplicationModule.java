package com.sync.architecture.blueprints.todoapp;

import android.content.Context;
import com.sync.architecture.blueprints.todoapp.data.source.Local;
import com.sync.architecture.blueprints.todoapp.data.source.Remote;
import com.sync.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.sync.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource;
import com.sync.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link
 * com.example.android.architecture.blueprints.todoapp.data.source.TasksRepositoryComponent}.
 */
@Module public final class ApplicationModule {

  private final Context mContext;

  ApplicationModule(Context context) {
    mContext = context;
  }

  @Provides Context provideContext() {
    return mContext;
  }
}