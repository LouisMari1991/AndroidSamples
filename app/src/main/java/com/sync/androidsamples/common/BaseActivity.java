package com.sync.androidsamples.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by YH on 2016/8/13.
 */
public class BaseActivity  extends AppCompatActivity {

  protected String TAG = getClass().getSimpleName();


  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);

  }

  protected final <E extends View> E getView(int id) {
    try {
      return (E) findViewById(id);
    } catch (ClassCastException ex) {
      LogUtils.e("Could not cast View to concrete class.", ex);
      throw ex;
    }
  }

}
