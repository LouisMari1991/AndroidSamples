package com.googlesamples.displayingbitmaps.util;

import android.common.logger.Log;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.googlesamples.displayingbitmaps.BuildConfig;

/**
 * Created by sync on 2016/6/3.
 */
public class RecyclingBitmapDrawable extends BitmapDrawable {

  static final String TAG = "RecyclingBitmapDrawable";


  private int mCacheRefCount = 0;
  private int mDisplayRefCount;

  private boolean mHasBeenDisplayed;


  public void setIsDisplayed(boolean isDisplayed) {
    synchronized (this) {
      if (isDisplayed) {
        mDisplayRefCount++;
        mHasBeenDisplayed = true;
      } else {
        mDisplayRefCount--;
      }
    }
    checkState();
  }


  public void setIsCached(boolean isCached) {
    synchronized (this) {
      if (isCached) {
        mCacheRefCount++;
      } else {
        mCacheRefCount--;
      }
    }
    checkState();
  }

  private synchronized void checkState() {
    if (mCacheRefCount <= 0 && mDisplayRefCount <= 0 && mHasBeenDisplayed && hasValidBitmap()) {
      if (BuildConfig.DEBUG) {
        Log.d(TAG, "No longer being used or cached so recycling");
      }
      getBitmap().recycle();
    }
    //END_INCLUDE(check_state)
  }


  private synchronized boolean hasValidBitmap() {
    Bitmap bitmap = getBitmap();
    return bitmap != null && !bitmap.isRecycled();
  }

}
