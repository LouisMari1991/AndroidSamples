package com.sync.scrollshapeui.utils;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

/**
 * Description:
 * Author：Mari on 2017-07-31 22:43
 * Contact：289168296@qq.com
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT) public class CustomChangeBounds extends ChangeBounds {

  @Override
  public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

    Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);

    changeBounds.setDuration(500);
    changeBounds.setInterpolator(
        AnimationUtils.loadInterpolator(sceneRoot.getContext(), android.R.interpolator.fast_out_slow_in));

    return changeBounds;
  }
}
