package com.sync.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.UUID;

/**
 * Description:
 * Author：SYNC on 2017/6/1 0001 22:46
 * Contact：289168296@qq.com
 */
public final class Task {

  @Nullable private final String mId;

  @Nullable private final String mTitle;

  @Nullable private final String mDescription;

  private final boolean mCompleted;

  public Task(@Nullable String title, @Nullable String description) {
    this(title, description, UUID.randomUUID().toString(), false);
  }

  public Task(@Nullable String title, @Nullable String description, @NonNull String id) {
    this(title, description, id, false);
  }

  public Task(@Nullable String title, @Nullable String description, boolean completed) {
    this(title, description, UUID.randomUUID().toString(), completed);
  }

  public Task(@Nullable String title, @Nullable String description, @NonNull String id, boolean completed) {
    mId = id;
    mTitle = title;
    mDescription = description;
    mCompleted = completed;
  }



}
