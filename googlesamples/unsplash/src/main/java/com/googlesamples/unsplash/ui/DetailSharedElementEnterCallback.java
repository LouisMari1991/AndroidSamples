package com.googlesamples.unsplash.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.app.SharedElementCallback;
import com.googlesamples.unsplash.databinding.DetailViewBinding;
import com.googlesamples.unsplash.databinding.PhotoItemBinding;

/**
 * Author：Administrator on 2016/9/2 0002 21:44
 * Contact：289168296@qq.com
 */
public class DetailSharedElementEnterCallback extends SharedElementCallback {

  private final Intent intent;
  private float targetTextSize;
  private ColorStateList targetTextColor;
  private DetailViewBinding currentDatailBinding;
  private PhotoItemBinding currentPhotoItemBinding;
  private Rect targetPadding;

  public DetailSharedElementEnterCallback(Intent intent) {
    this.intent = intent;
  }



}
