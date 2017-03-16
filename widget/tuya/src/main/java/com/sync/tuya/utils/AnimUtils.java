package com.sync.tuya.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by YH on 2017-03-16.
 */

public class AnimUtils {

  public static final Animation getItemAlpha(int position) {
    Animation anim = new AlphaAnimation(0.0f, 1.0f);
    anim.setDuration(300);
    anim.setFillAfter(true);
    anim.setStartOffset((long) (position * 100));
    return anim;
  }

  public static final Animation getItemTran(int position, int width) {
    Animation anim = new TranslateAnimation((float) width, 0.0f, 0.0f, 0.0f);
    anim.setDuration(300);
    anim.setFillAfter(true);
    anim.setStartOffset((long) (position * 100));
    return anim;
  }


}
