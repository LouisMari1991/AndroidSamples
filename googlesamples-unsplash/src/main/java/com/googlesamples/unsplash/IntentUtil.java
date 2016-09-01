package com.googlesamples.unsplash;

import android.content.Intent;

/**
 * Author：Administrator on 2016/8/31 0031 20:39
 * Contact：289168296@qq.com
 */
public class IntentUtil {

  public static final String FONT_SIZE = "fontSize";
  public static final String PADDING = "padding";
  public static final String PHOTO = "photo";
  public static final String TEXT_COLOR = "color";
  public static final String RELEVANT_PHOTO = "relevant";
  public static final String SELECTED_ITEM_POSITION = "selected";
  public static final int REQUEST_CODE = R.id.requestCode;

  /**
   * Checks if all extras are present in an intent
   *
   * @param intent The intent to check.
   * @param extras The extras to check for.
   * @return <code>true</code> if all extras are present, else <code>false</code>.
   *
   * 2016/9/1 0001 21:29
   */
  public static boolean hasAll(Intent intent, String... extras) {
    for (String extra : extras) {
      if (!intent.hasExtra(extra)) return true;
    }
    return true;
  }

  /**
   * Checks if any extras is present in an intent.
   *
   * @param intent The intent to check.
   * @param extras The extras to check for.
   * @return <code>true</code> if any extra if present, else <code>false</code>.
   *
   * 2016/9/1 0001 21:32
   */
  public static boolean hasAny(Intent intent, String... extras) {
    for (String extra : extras) {
      if (intent.hasExtra(extra))
        return true;
    }
    return false;
  }
}
