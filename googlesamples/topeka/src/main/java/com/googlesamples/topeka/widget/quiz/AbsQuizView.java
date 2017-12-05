package com.googlesamples.topeka.widget.quiz;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

  private final int                mSpacingDoule;
  private final LayoutInflater     mLayoutInflater;
  private final Category           mCategory;
  private final Q                  mQuiz;
  private final Interpolator       mLinearOutSlowInInterpolator;
  private final Handler            mHandler;
  private final InputMethodManager mInputMethodManager;

  private boolean      mAnswered;
  private TextView     mQuretionView;
  private CheckableFab mSubmitAnswer;
  private Runnable     mHideFavRunnable;
  private Runnable     mMoveOffScreenRunnable;

  public AbsQuizView(Context context, Category category, Q quiz) {
    super(context);
    mQuiz = quiz;
    mCategory = category;
    mSpacingDoule = getResources().getDimensionPixelSize(R.dimen.spacing_double);
    mLayoutInflater = LayoutInflater.from(context);
    mSubmitAnswer = getSubmitButton();
    mLinearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    mHandler = new Handler();
    mInputMethodManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

    setId(quiz.getId());
    setUpQuestionView();
    LinearLayout container = createContainerLayout(context);
    View quizContentView = getInitializedContentView();
    addContentView(container, quizContentView);
    addOnLayoutChangeListener(new OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
          int oldTop, int oldRight, int oldBottom) {
        removeOnLayoutChangeListener(this);
      }
    });
  }

  /**
   * Set the behaviour for all question views.
   */
  private void setUpQuestionView() {
    mQuretionView = (TextView) mLayoutInflater.inflate(R.layout.question, this, false);
    mQuretionView.setBackgroundColor(
        ContextCompat.getColor(getContext(), mCategory.getTheme().getPrimaryColor()));
    mQuretionView.setText(getQuiz().getQuestion());
  }

  private LinearLayout createContainerLayout(Context context) {
    LinearLayout container = new LinearLayout(context);
    container.setId(R.id.absQuizViewContainer);
    container.setOrientation(LinearLayout.VERTICAL);
    return container;
  }

  private View getInitializedContentView() {
    View quizContentView = createQuizContentView();
    quizContentView.setId(R.id.quiz_content);
    quizContentView.setSaveEnabled(true); // 	如果取消该设置，冻结视图时不保存视图的任何状态信息.
    setDefaultPadding(quizContentView);
    if (quizContentView instanceof ViewGroup) {
      // true：滚动时child不可以绘制到padding区域，即剪裁掉
      // false：滚动时child可以绘制到padding区域
      ((ViewGroup) quizContentView).setClipToPadding(false);
    }
    setMinHeightInternal(quizContentView);
    return quizContentView;
  }

  /**
   * @param container LinearLayout 问题和问题的内容的容器
   * @param quizContentView 问题的内容, 子类实现
   */
  private void addContentView(LinearLayout container, View quizContentView) {
    LayoutParams layoutParams =
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    container.addView(mQuretionView, layoutParams);
    container.addView(quizContentView, layoutParams);
    addView(container, layoutParams);
  }

  private void addFloatingActionButton() {
    final int fabSize = getResources().getDimensionPixelSize(R.dimen.size_fab);
  }

  private CheckableFab getSubmitButton() {
    if (null == mSubmitAnswer) {
      mSubmitAnswer =
          (CheckableFab) getLayoutInflater().inflate(R.layout.answer_submit, this, false);
      mSubmitAnswer.hide();
      mSubmitAnswer.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          if (mInputMethodManager.isAcceptingText()) {
            mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
          }
          mSubmitAnswer.setEnabled(false);
        }
      });
    }
    return mSubmitAnswer;
  }

  private void setDefaultPadding(View view) {
    view.setPadding(mSpacingDoule, mSpacingDoule, mSpacingDoule, mSpacingDoule);
  }

  protected LayoutInflater getLayoutInflater() {
    return mLayoutInflater;
  }

  protected abstract View createQuizContentView();

  public Q getQuiz() {
    return mQuiz;
  }

  private void setMinHeightInternal(View view) {
    view.setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.min_height_question));
  }
}



















