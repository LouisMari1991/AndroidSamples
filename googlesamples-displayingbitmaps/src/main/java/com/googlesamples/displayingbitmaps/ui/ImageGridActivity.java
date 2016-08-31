package com.googlesamples.displayingbitmaps.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import com.googlesamples.displayingbitmaps.R;

/**
 * Created by sync on 2016/5/15.
 */
public class ImageGridActivity extends FragmentActivity {

  private static final String TAG = "ImageGridActivity";


  @Override
  public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setContentView(R.layout.activity_main);
    
  }


}
