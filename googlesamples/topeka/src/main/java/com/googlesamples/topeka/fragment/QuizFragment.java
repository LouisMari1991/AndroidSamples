package com.googlesamples.topeka.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.AdapterViewAnimator;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.googlesamples.topeka.adapter.ScoreAdapter;
import com.googlesamples.topeka.model.Category;

/**
 * Author：Administrator on 2016/8/20 0020 17:39
 * Contact：289168296@qq.com
 */
public class QuizFragment extends Fragment {

  private static final String KEY_USER_INPUT = "USER_INPUT";
  private TextView            mProgressText;
  private int                 mQuizSize;
  private ProgressBar         mProgressBar;
  private Category            mCategory;
  private AdapterViewAnimator mQuizView;
  private ScoreAdapter        mScoreAdapter;
  private SolvedStateListener mSolvedStateListener;

  public static QuizFragment newInstance(String categoryId, SolvedStateListener solvedStateListener) {
    if (categoryId == null) {
      throw new IllegalArgumentException("The category can not be null");
    }
    Bundle args = new Bundle();
    args.putString(Category.TAG, categoryId);
    QuizFragment fragment = new QuizFragment();
    if (solvedStateListener != null) {
      fragment.mSolvedStateListener = solvedStateListener;
    }
    fragment.setArguments(args);
    return fragment;
  }

  private ScoreAdapter getScoreAdapter() {
    if (null == mScoreAdapter) {
      mScoreAdapter = new ScoreAdapter(mCategory);
    }
    return mScoreAdapter;
  }

  public boolean hasSolvedStateListener() {
    return mSolvedStateListener != null;
  }

  public void setSolvedStateListener(SolvedStateListener solvedStateListener) {
    mSolvedStateListener = solvedStateListener;
    if (mCategory.isSolved() && null != mSolvedStateListener) {
      mSolvedStateListener.onCategorySolved();
    }
  }

  /**
   * Interface definition for a callback to be invoked when the quiz is started.
   */
  public interface SolvedStateListener {
    /**
     * This method will be invoked when the category has been solved.
     */
    void onCategorySolved();
  }
}
