package com.sync.androidsamples.google.samples.apps.topeka.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import com.sync.androidsamples.google.samples.apps.topeka.model.Player;

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


  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    
  }
}
