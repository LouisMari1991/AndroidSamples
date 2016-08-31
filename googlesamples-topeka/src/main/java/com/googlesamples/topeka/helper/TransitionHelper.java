package com.googlesamples.topeka.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author：Administrator on 2016/8/20 0020 15:35
 * Contact：289168296@qq.com
 */
public class TransitionHelper {

  private TransitionHelper() {
    // no instance
  }

  /**
   * Create the transition participants required during a activity transition while avoiding
   * glitches with the system UI.
   *
   * @param activity The acticity used as start for the transition.
   * @param includeStateBar If false, the status bar will not be added as the transition
   * participant.
   * @return All transition participants
   * @date 2016/8/20 0020 16:07
   */
  public static Pair<View, String>[] createSafeTransitionParticipants(@NonNull Activity activity,
      boolean includeStateBar, @NonNull Pair... otherParticipants) {

    // Avoid system UI glitches as described here.
    View decor = activity.getWindow().getDecorView();
    View statusBar = null;
    if (includeStateBar) {
      statusBar = decor.findViewById(android.R.id.statusBarBackground);
    }
    View navBar = decor.findViewById(android.R.id.navigationBarBackground);

    // Create pair of transition participants
    List<Pair> participants = new ArrayList<>(3);
    addNonNullViewToTransitionParticipants(statusBar, participants);
    addNonNullViewToTransitionParticipants(navBar, participants);
    // only add transition participants if there's at least ont none-null element
    if (otherParticipants != null && !(otherParticipants.length == 1
        && otherParticipants[0] == null)) {
      participants.addAll(Arrays.asList(otherParticipants));
    }
    // noinspection unchecked
    return participants.toArray(new Pair[participants.size()]);
  }

  private static void addNonNullViewToTransitionParticipants(View view, List<Pair> participants) {
    if (view == null) {
      return;
    }
    participants.add(new Pair<>(view, view.getTransitionName()));
  }
}
