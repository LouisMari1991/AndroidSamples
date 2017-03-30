package com.sync.architecture.blueprints.todoapp.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.common.base.Preconditions;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年03月30日 16:43
 */
public class ActivityUtils {

  /**
   * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
   * performed by the {@code fragmentManager}.
   */
  public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment,
      int frameId) {
    Preconditions.checkNotNull(fragmentManager);
    Preconditions.checkNotNull(fragment);
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(frameId, fragment);
    fragmentTransaction.commit();
  }
}
