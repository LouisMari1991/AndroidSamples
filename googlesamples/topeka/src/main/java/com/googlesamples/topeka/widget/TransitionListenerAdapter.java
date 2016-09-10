package com.googlesamples.topeka.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;

/**
 * Empty implementation of {@link Transition.TransitionListener}
 *
 * Created by YH on 2016/9/10.
 */
@TargetApi(Build.VERSION_CODES.KITKAT) public class TransitionListenerAdapter implements Transition.TransitionListener{

  @Override public void onTransitionStart(Transition transition) {

  }

  @Override public void onTransitionEnd(Transition transition) {

  }

  @Override public void onTransitionCancel(Transition transition) {

  }

  @Override public void onTransitionPause(Transition transition) {

  }

  @Override public void onTransitionResume(Transition transition) {

  }
}
