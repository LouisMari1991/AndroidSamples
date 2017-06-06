package com.sync.architecture.blueprints.todoapp;

import android.app.Application;
import com.sync.architecture.blueprints.todoapp.data.source.TasksRepositoryComponent;

/**
 * Description:
 * Author：SYNC on 2017/6/1 0001 22:45
 * Contact：289168296@qq.com
 */
public class ToDoApplication extends Application {

  private TasksRepositoryComponent mRepositoryComponent;

  @Override public void onCreate() {
    super.onCreate();
    //mRepositoryComponent = DaggerTasksRepositoryComponent.builder()
    //    .applicationModule(new ApplicationModule((getApplicationContext())))
    //    .build();
  }

  public TasksRepositoryComponent getTasksRepositoryComponent() {
    return mRepositoryComponent;
  }
}
