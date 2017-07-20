package com.googlesamples.topeka.widget.quiz;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.model.Category;
import com.googlesamples.topeka.model.quiz.Quiz;
import com.googlesamples.topeka.widget.fab.CheckableFab;

/**
 * This is the base class for displaying a {@link Quiz}.
 *
 * Created by YH on 2017-02-24.
 */

public abstract class AbsQuizView<Q extends Quiz> extends FrameLayout {

  private static final int ANSWER_HIDE_DELAY             = 500;
  private static final int FOREGROUND_COLOR_CHANGE_DELAY = 750;
  private final int mSpacingDoule;
  private final LayoutInflater mLayoutInflater;
  private final Category mCategory;
  private final Q mQuiz;
  private final Interpolator mLinearOutSlowInInterpolator;
  private final Handler mHandler;
  private final InputMethodManager mInputMethodManager;
  private boolean mAnswered;
  private TextView mQuretionView;
  private CheckableFab mSubmitAnswer;
  private Runnable mHideFavRunnable;
  private Runnable mMoveOffScreenRunnable;

  public AbsQuizView(Context context, Category category, Q quiz) {
    super(context);
    mQuiz = quiz;
    mCategory = category;
    mSpacingDoule = getResources().getDimensionPixelSize(R.dimen.spacing_double);
    mLayoutInflater = LayoutInflater.from(context);
    //mSubmitAnswer = gets
    mLinearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    mHandler = new Handler();
    mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

    setId(quiz.getId());

  }
}
