package com.sync.androidsamples.common;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by YH on 2016/8/13.
 */
public class BaseActivity  extends AppCompatActivity {

  protected String TAG = getClass().getSimpleName();

  protected final <E extends View> E getView(int id) {
    try {
      return (E) findViewById(id);
    } catch (ClassCastException ex) {
      Log.e(TAG, "Could not cast View to concrete class.", ex);
      throw ex;
    }
  }


}
