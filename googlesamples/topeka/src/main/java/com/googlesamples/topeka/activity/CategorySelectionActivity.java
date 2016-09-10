package com.googlesamples.topeka.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.databinding.ActivityCategorySelectionBinding;
import com.googlesamples.topeka.fragment.CategorySelectionFragment;
import com.googlesamples.topeka.helper.ApiLevelHelper;
import com.googlesamples.topeka.helper.PreferencesHelper;
import com.googlesamples.topeka.model.Player;
import com.googlesamples.topeka.persistence.TopekaDatabaseHelper;

/**
 * Created by YH on 2016/8/13.
 */
public class CategorySelectionActivity extends AppCompatActivity {

  private static final String EXTRA_PLAYER = "player";

  public static void start(Activity activity, Player player, ActivityOptionsCompat options) {
    Intent starter = getStartIntent(activity, player);
    ActivityCompat.startActivity(activity, starter, options.toBundle());
  }

  public static void start(Context context, Player player) {
    Intent starter = getStartIntent(context, player);
    context.startActivity(starter);
  }

  @NonNull static Intent getStartIntent(Context context, Player player) {
    Intent starter = new Intent(context, CategorySelectionActivity.class);
    starter.putExtra(EXTRA_PLAYER, player);
    return starter;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityCategorySelectionBinding binding = DataBindingUtil
        .setContentView(this, R.layout.activity_category_selection);
    Player player = getIntent().getParcelableExtra(EXTRA_PLAYER);
    if (!PreferencesHelper.isSignedIn(this)) {
      if (player == null) {
        player = PreferencesHelper.getPlayer(this);
      } else {
        PreferencesHelper.writeToPreferences(this, player);
      }
    }
    binding.setPlayer(player);
    setUpToolbar();
    if (savedInstanceState == null) {
      attachCategoryGridFragment();
    } else {
      setProgressBarVisibility(View.GONE);
    }
    supportPostponeEnterTransition();
  }

  @Override protected void onResume() {
    super.onResume();
    TextView scoreView = (TextView) findViewById(R.id.score);
    final int score = TopekaDatabaseHelper.getScore(this);
    scoreView.setText(getString(R.string.x_points, score));
  }

  private void setUpToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
    setSupportActionBar(toolbar);
    //noinspection ConstantConditions
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    //final AvatarView avatarView = (AvatarView) toolbar.findViewById(R.id.avatar);
    //avatarView.setAvatar(player.getAvatar().getDrawableId());
    // noinspection PrivateResource
    //((TextView) toolbar.findViewById(R.id.title)).setText(getDisplayName(player));
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_category, menu);
    return true;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.category_container);
    if (fragment != null) {
      fragment.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.sign_out:
        signOut();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressLint("NewApi")
  private void signOut() {
    PreferencesHelper.signOut(this);
    TopekaDatabaseHelper.reset(this);
    if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
      getWindow().setExitTransition(
          TransitionInflater.from(this).inflateTransition(R.transition.category_enter));
    }
    SignInActivity.start(this, false);
    finish();
    //ActivityCompat.finishAfterTransition(this);
  }

  //private String getDisplayName(Player player) {
  //  return getString(R.string.player_display_name, player.getFirstName(), player.getLastInitial());
  //}

  private void attachCategoryGridFragment() {
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    Fragment fragment = supportFragmentManager.findFragmentById(R.id.category_container);
    if (!(fragment instanceof CategorySelectionFragment)) {
      fragment = CategorySelectionFragment.newInstance();
    }
    supportFragmentManager.beginTransaction().replace(R.id.category_container, fragment).commit();
    setProgressBarVisibility(View.GONE);
  }

  private void setProgressBarVisibility(int visibility) {
    findViewById(R.id.progress).setVisibility(visibility);
  }
}























