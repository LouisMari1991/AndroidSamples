package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;

/**
 * Description:
 * Author：SYNC on 2017-07-04 23:03
 * Contact：289168296@qq.com
 */
public class ToggleTranslateQuiz extends OptionsQuiz<String[]> {

  private String[] mReadableOptions;

  protected ToggleTranslateQuiz(String question, int[] answer, String[][] options, boolean solved) {
    super(question, answer, options, solved);
  }

  protected ToggleTranslateQuiz(Parcel in) {
    super(in);
    setAnswer(in.createIntArray());
    setOptions((String[][]) in.readSerializable());
  }

  @Override public QuizType getType() {
    return null;
  }

  @Override public String getStringAnswer() {
    return null;
  }
}
