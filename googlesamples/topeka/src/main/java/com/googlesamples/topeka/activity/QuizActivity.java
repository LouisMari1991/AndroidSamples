package com.googlesamples.topeka.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.googlesamples.topeka.fragment.QuizFragment;
import com.googlesamples.topeka.model.Category;

/**
 * Created by YH on 2016/8/13.
 */
public class QuizActivity extends AppCompatActivity {

  private static final String TAG              = "QuizActivity";
  private static final String IMAGE_CATEGORY   = "image_category";
  private static final String STATE_IS_PLAYING = "isPlaying";
  private static final String FRAGMENT_TAG     = "Quiz";

  private Interpolator           mInterpolator;
  private Category               mCategory;
  private QuizFragment           mQuizFragment;
  private FloatingActionButton   mQuizFab;
  private boolean                mSavedStateIsPlaying;
  private ImageView              mIcon;
  private Animator               mCircularReveal;
  private ObjectAnimator         mColorChange;
  private CountingIdlingResource mCountingIdlingResource;
  private View                   mToolbarBack;

  private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      switch (v.getId()) {

      }
    }
  };

  public static Intent getStartIntent(Context context, Category category) {
    Intent starter = new Intent(context, QuizActivity.class);
    starter.putExtra(Category.TAG, category.getId());
    return starter;
  }

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }
}
