package com.sync.architecture.blueprints.todoapp.data.source;

import android.support.annotation.NonNull;
import com.sync.architecture.blueprints.todoapp.data.Task;
import java.util.List;

/**
 * Description:
 * Author：SYNC on 2017/6/5 0005 22:38
 * Contact：289168296@qq.com
 */
public interface TasksDataSource {

  interface LoadTasksCallback {

    void onTasksLoaded(List<Task> tasks);

    void onDataNotAvailable();
  }

  interface GetTaskCallback {

    void onTaskLoaded(Task task);

    void onDataNotAvailable();
  }

  void getTasks(@NonNull LoadTasksCallback callback);

  void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

  void saveTask(@NonNull Task task);

  void completeTask(@NonNull Task task);

  void completedTask(@NonNull String taskId);

  void activateTask(@NonNull Task task);

  void activateTask(@NonNull String taskId);

  void clearCompletedTasks();

  void refreshTasks();

  void deletedAllTasks();

  void deleteTask(@NonNull String taskId);
}
