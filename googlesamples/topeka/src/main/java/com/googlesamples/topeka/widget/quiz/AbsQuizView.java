package com.googlesamples.topeka.widget.quiz;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.googlesamples.topeka.model.quiz.Quiz;

/**
 * This is the base class for displaying a {@link Quiz}.
 *
 * Created by YH on 2017-02-24.
 */

public class AbsQuizView<Q extends Quiz> extends FrameLayout {



  public AbsQuizView(Context context) {
    super(context);
  }

  public AbsQuizView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AbsQuizView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
}
