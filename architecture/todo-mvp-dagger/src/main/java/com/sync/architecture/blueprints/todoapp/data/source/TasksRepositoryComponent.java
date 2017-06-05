package com.sync.architecture.blueprints.todoapp.data.source;

import com.sync.architecture.blueprints.todoapp.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Description:
 * Author：SYNC on 2017/6/5 0005 22:35
 * Contact：289168296@qq.com
 */
@Singleton @Component(modules = { ApplicationModule.class }) public interface TasksRepositoryComponent {
  TasksRepository getTasksRepository();
}
