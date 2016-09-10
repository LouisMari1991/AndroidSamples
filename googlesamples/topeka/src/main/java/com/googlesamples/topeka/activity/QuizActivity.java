package com.googlesamples.topeka.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import com.googlesamples.topeka.model.Category;

/**
 * Created by YH on 2016/8/13.
 */
public class QuizActivity extends AppCompatActivity {

  public static Intent getStartIntent(Context context, Category category) {
    Intent starter = new Intent(context, QuizActivity.class);
    starter.putExtra(Category.TAG, category.getId());
    return starter;
  }


  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);

  }
}
