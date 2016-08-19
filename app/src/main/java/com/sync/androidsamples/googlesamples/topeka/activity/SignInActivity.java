package com.sync.androidsamples.googlesamples.topeka.topeka.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import com.sync.androidsamples.R;

/**
 * Created by YH on 2016/8/13.
 */
public class SignInActivity extends AppCompatActivity{

  private static final String EXTRA_EDIT = "EDIT";

  public static void start(Activity activity, Boolean edit){
    Intent starter = new Intent(activity, SignInActivity.class);
    starter.putExtra(EXTRA_EDIT, edit);
    // noinspection unchecked
    ActivityCompat.startActivity(activity,starter, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
  }


  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setContentView(R.layout.activity_sign_in);
    final boolean edit = isInEditMode();
    if (savedInstanceState == null){
      //getSupportFragmentManager().beginTransaction().r
    }
  }

  private boolean isInEditMode(){
    final Intent intent = getIntent();
    boolean edit = false;
    if (null != intent){
      edit = intent.getBooleanExtra(EXTRA_EDIT, false);
    }
    return edit;
  }

}
