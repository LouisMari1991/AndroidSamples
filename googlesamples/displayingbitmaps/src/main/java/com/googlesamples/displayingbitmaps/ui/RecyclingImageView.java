package com.googlesamples.displayingbitmaps.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.googlesamples.displayingbitmaps.util.RecyclingBitmapDrawable;

/**
 * Sub-class of ImageView which automatically notifies the the drawable when it is
 * being displayed.
 * Created by sync on 2016/5/15.
 */
public class RecyclingImageView extends ImageView {

  public RecyclingImageView(Context context) {
    super(context);
  }

  public RecyclingImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * @see ImageView#onDetachedFromWindow()
   */
  @Override
  protected void onDetachedFromWindow() {
    //This has been detached from window, so clear the drawable
    setImageDrawable(null);
    super.onDetachedFromWindow();
  }


  /**
   * @see android.widget.ImageView#setImageDrawable(Drawable)
   */
  @Override
  public void setImageDrawable(Drawable drawable) {
    // Keep hold of previous Drawable
    final Drawable previousDrawable = getDrawable();

    // Call super to set new Drawable
    super.setImageDrawable(drawable);

    // Notify new Drawable that it is being displayed
    notifyDrawable(drawable, true);

    // Notify old Drawable so it is no longer being displayed
    notifyDrawable(previousDrawable, false);
  }


  /**
   * Notifies the drawable that it's displayed state has changed
   * @param drawable
   * 
   * @param isDisplayed
   */
  private static void notifyDrawable(Drawable drawable, final boolean isDisplayed) {
    if (drawable instanceof RecyclingBitmapDrawable) {
      ((RecyclingBitmapDrawable)drawable).setIsDisplayed(isDisplayed);
    } else if (drawable instanceof LayerDrawable) {
      LayerDrawable layerDrawable = (LayerDrawable) drawable;
      for (int i = 0, z = layerDrawable.getNumberOfLayers(); i < z; i++){
        notifyDrawable(layerDrawable.getDrawable(i), isDisplayed);
      }
    }
  }

}
