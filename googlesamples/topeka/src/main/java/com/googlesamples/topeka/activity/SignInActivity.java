package com.googlesamples.topeka.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.fragment.SignInFragment;
import com.googlesamples.topeka.helper.PreferencesHelper;

/**
 * Created by YH on 2016/8/13.
 */
public class SignInActivity extends AppCompatActivity {

  private static final String EXTRA_EDIT = "EDIT";

  public static void start(Activity activity, Boolean edit) {
    Intent starter = new Intent(activity, SignInActivity.class);
    starter.putExtra(EXTRA_EDIT, edit);
    // noinspection unchecked
    ActivityCompat.startActivity(activity,
        starter,
        ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    final boolean edit = isInEditMode();
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.sign_in_container, SignInFragment.newInstance(edit))
          .commit();
    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (PreferencesHelper.isSignedIn(this)) {
      finish();
    }
  }

  private boolean isInEditMode() {
    final Intent intent = getIntent();
    boolean edit = false;
    if (null != intent) {
      edit = intent.getBooleanExtra(EXTRA_EDIT, false);
    }
    return edit;
  }
}
