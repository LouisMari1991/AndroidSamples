package com.googlesamples.unsplash.ui;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlesamples.unsplash.IntentUtil;
import com.googlesamples.unsplash.databinding.DetailViewBinding;
import com.googlesamples.unsplash.databinding.PhotoItemBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author：Administrator on 2016/9/2 0002 21:44
 * Contact：289168296@qq.com
 */
public class DetailSharedElementEnterCallback extends SharedElementCallback {

  private final Intent intent;
  private float targetTextSize;
  private ColorStateList targetTextColors;
  private DetailViewBinding currentDatailBinding;
  private PhotoItemBinding currentPhotoBinding;
  private Rect targetPadding;

  public DetailSharedElementEnterCallback(Intent intent) {
    this.intent = intent;
  }

  @Override
  public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
      List<View> sharedElementSnapshots) {
    TextView author = getAuthor();
    targetTextSize = author.getTextSize();
    targetTextColors = author.getTextColors();
    targetPadding =
        new Rect(author.getPaddingLeft(), author.getPaddingTop(), author.getPaddingRight(),
            author.getPaddingBottom());
    if (IntentUtil.hasAll(intent, IntentUtil.TEXT_COLOR, IntentUtil.FONT_SIZE,
        IntentUtil.PADDING)) {
      author.setTextColor(intent.getIntExtra(IntentUtil.TEXT_COLOR, Color.BLACK));
      float textSize = intent.getFloatExtra(IntentUtil.FONT_SIZE, targetTextSize);
      author.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
      Rect padding = intent.getParcelableExtra(IntentUtil.PADDING);
      author.setPadding(padding.left, padding.top, padding.right, padding.bottom);
    }
  }

  @Override
  public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements,
      List<View> sharedElementSnapshots) {
    TextView author = getAuthor();
    author.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize);
    if (targetTextColors != null) {
      author.setTextColor(targetTextColors);
    }
    if (targetPadding != null) {
      author.setPadding(targetPadding.left, targetPadding.top, targetPadding.right,
          targetPadding.bottom);
    }
    if (currentDatailBinding != null) {
      forceSharedElementLayout(currentDatailBinding.description);
    }
  }

  @Override public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
    //remo
  }

  public void setBinding(@NonNull DetailViewBinding binding) {
    currentDatailBinding = binding;
    currentPhotoBinding = null;
  }

  public void setBinding(@NonNull PhotoItemBinding binding) {
    currentPhotoBinding = binding;
    currentDatailBinding = null;
  }

  private TextView getAuthor() {
    if (currentPhotoBinding != null) {
      return currentPhotoBinding.author;
    } else if (currentDatailBinding != null) {
      return currentDatailBinding.author;
    } else {
      throw new NullPointerException("Must set a binding before transitioning.");
    }
  }

  private ImageView getPhoto() {
    if (currentPhotoBinding != null) {
      return currentPhotoBinding.photo;
    } else if (currentDatailBinding != null) {
      return currentDatailBinding.photo;
    } else {
      throw new NullPointerException("Must set a binding before transitioning.");
    }
  }

  /**
   * Maps all views that don't start with "android" namespace.
   *
   * @param names All shared element names.
   * @return The obsolete shared element names.
   * @author YH
   * @time 2016-11-30 14:27
   */
  private List<String> mapObsoleteElements(List<String> names) {
    List<String> elementsToRemove = new ArrayList<>();
    for (String name : names) {
      if (name.startsWith("android")) continue;
      elementsToRemove.add(name);
    }
    return elementsToRemove;
  }

  /**
   * Removes obsolete elements from names and shared elements.
   *
   * @param names Shared element names.
   * @param sharedElements Shared elements.
   * @param elementsToRemove The elements that should be removed.
   *
   * @author YH
   * @time 2016-11-30 14:31
   */
  private void removeObsoleteElements(List<String> names, Map<String, View> sharedElements,
      List<String> elementsToRemove) {
    if (elementsToRemove.size() > 0) {
      names.removeAll(elementsToRemove);
      for (String elementToRemove : elementsToRemove) {
        sharedElements.remove(elementToRemove);
      }
    }
  }

  private void forceSharedElementLayout(View view) {
    int widthSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY);
    int heightSpec = View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY);
    view.measure(widthSpec, heightSpec);
    view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
  }
}
