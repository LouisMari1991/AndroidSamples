package com.sync.architecture.blueprints.todoapp.data.source;

import android.support.annotation.NonNull;
import com.sync.architecture.blueprints.todoapp.data.Task;
import javax.inject.Singleton;

/**
 * Description:
 * Author：SYNC on 2017/6/6 0006 00:19
 * Contact：289168296@qq.com
 */
@Singleton public class TasksRepository implements TasksDataSource {
  @Override public void getTasks(@NonNull LoadTasksCallback callback) {

  }

  @Override public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

  }

  @Override public void saveTask(@NonNull Task task) {

  }

  @Override public void completeTask(@NonNull Task task) {

  }

  @Override public void completedTask(@NonNull String taskId) {

  }

  @Override public void activateTask(@NonNull Task task) {

  }

  @Override public void activateTask(@NonNull String taskId) {

  }

  @Override public void clearCompletedTasks() {

  }

  @Override public void refreshTasks() {

  }

  @Override public void deletedAllTasks() {

  }

  @Override public void deleteTask(@NonNull String taskId) {

  }
}
