package com.sync.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import java.util.UUID;

/**
 * Description:
 * Author：SYNC on 2017/6/1 0001 22:46
 * Contact：289168296@qq.com
 */
public final class Task {

  /**
   * @NonNull 表明不能为空
   */
  @NonNull private final String mId;

  /**
   * @Nullable 表明可以为空
   */
  @Nullable private final String mTitle;

  /**
   * @Nullable 表明可以为空
   */
  @Nullable private final String mDescription;

  private final boolean mCompleted;

  /**
   * Use this constructor to create a new active Task.
   *
   * @param title title of the task
   * @param description description of the task
   */
  public Task(@Nullable String title, @Nullable String description) {
    this(title, description, UUID.randomUUID().toString(), false);
  }

  /**
   * Use this constructor to create an active Task if the Task already has an id (copy of another
   * Task).
   *
   * @param title title of the task
   * @param description description of the task
   * @param id id of the task
   */
  public Task(@Nullable String title, @Nullable String description, @NonNull String id) {
    this(title, description, id, false);
  }

  /**
   * Use this constructor to create a new completed Task.
   *
   * @param title title of the task
   * @param description description of the task
   * @param completed true if the task is completed, false if it's active
   */
  public Task(@Nullable String title, @Nullable String description, boolean completed) {
    this(title, description, UUID.randomUUID().toString(), completed);
  }

  /**
   * Use this constructor to specify a completed Task if the Task already has an id (copy of
   * another Task).
   *
   * @param title title of the task
   * @param description description of the task
   * @param id id of the task
   * @param completed true if the task is completed, false if it's active
   */
  public Task(@Nullable String title, @Nullable String description, @NonNull String id, boolean completed) {
    mId = id;
    mTitle = title;
    mDescription = description;
    mCompleted = completed;
  }

  @NonNull public String getId() {
    return mId;
  }

  @Nullable public String getTitle() {
    return mTitle;
  }

  @Nullable public String getTitleForList() {
    if (!Strings.isNullOrEmpty(mTitle)) {
      return mTitle;
    } else {
      return mDescription;
    }
  }

  @Nullable public String getDescription() {
    return mDescription;
  }

  public boolean isCompleted() {
    return mCompleted;
  }

  public boolean isActive() {
    return !mCompleted;
  }

  public boolean isEmpty() {
    return Strings.isNullOrEmpty(mTitle) && Strings.isNullOrEmpty(mDescription);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return Objects.equal(mId, task.mId) && Objects.equal(mTitle, task.mTitle) && Objects.equal(mDescription,
        task.mDescription);
  }

  @Override public int hashCode() {
    return Objects.hashCode(mId, mTitle, mDescription);
  }

  @Override public String toString() {
    return "Task with title " + mTitle;
  }
}
