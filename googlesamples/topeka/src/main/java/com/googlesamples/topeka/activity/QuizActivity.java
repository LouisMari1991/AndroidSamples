package com.googlesamples.topeka.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.fragment.QuizFragment;
import com.googlesamples.topeka.model.Category;
import com.googlesamples.topeka.persistence.TopekaDatabaseHelper;
import com.sync.logger.Logger;

import static com.googlesamples.topeka.adapter.CategoryAdapter.DRAWABLE;

/**
 * Created by YH on 2016/8/13.
 */
public class QuizActivity extends AppCompatActivity {

  private static final String TAG              = "QuizActivity";
  private static final String IMAGE_CATEGORY   = "image_category_";
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
        case R.id.fab_quiz:

          break;
        case R.id.submitAnswer:

          break;
        case R.id.quiz_done:

          break;
        case R.id.back:
          onBackPressed();
          break;
        default:
          throw new UnsupportedOperationException(
              "OnClick has not been implemented for " + getResources().getResourceName(v.getId()));
      }
    }
  };

  public static Intent getStartIntent(Context context, Category category) {
    Intent starter = new Intent(context, QuizActivity.class);
    starter.putExtra(Category.TAG, category.getId());
    return starter;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    mCountingIdlingResource = new CountingIdlingResource("Quiz");
    String categoryId = getIntent().getStringExtra(Category.TAG);
    mInterpolator = new FastOutSlowInInterpolator();
    if (null != savedInstanceState) {
      mSavedStateIsPlaying = savedInstanceState.getBoolean(STATE_IS_PLAYING);
    }
    super.onCreate(savedInstanceState);
    populate(categoryId);
    int categoryNameTextSize = getResources()
        .getDimensionPixelSize(R.dimen.category_item_text_size);
    int paddingStart = getResources().getDimensionPixelSize(R.dimen.spacing_double);
    final int startDelay = getResources().getInteger(R.integer.toolbar_transition_duration);
    //ActivityCompat.setEnterSharedElementCallback(this, new TextShare);

  }

  private void populate(String categoryId) {
    if (null == categoryId) {
      Logger.w("Didn't find a category. Finishing");
      finish();
    }
    mCategory = TopekaDatabaseHelper.getCategoryWith(this, categoryId);
    //noinspection PrivateResource
    mIcon = (ImageView) findViewById(R.id.icon);
    int redId =
        getResources().getIdentifier(IMAGE_CATEGORY + categoryId, DRAWABLE, getApplicationContext().getPackageName());
    mIcon.setImageResource(redId);
    ViewCompat.animate(mIcon).scaleX(1).scaleY(1).alpha(1).setInterpolator(mInterpolator).setStartDelay(300).start();
    mQuizFab = (FloatingActionButton) findViewById(R.id.fab_quiz);
    mQuizFab.setImageResource(R.drawable.ic_play);
    if (mSavedStateIsPlaying) {
      mQuizFab.hide();
    } else {
      mQuizFab.show();
    }
    mQuizFab.setOnClickListener(mOnClickListener);
  }

  private void initToolbar(Category category) {
    mToolbarBack = findViewById(R.id.back);
    mToolbarBack.setOnClickListener(mOnClickListener);
    TextView titleView = (TextView) findViewById(R.id.category_title);
    titleView.setText(category.getName());
    titleView.setTextColor(ContextCompat.getColor(this, category.getTheme().getTextPrimaryColor()));
    if (mSavedStateIsPlaying){

    }
  }
}
