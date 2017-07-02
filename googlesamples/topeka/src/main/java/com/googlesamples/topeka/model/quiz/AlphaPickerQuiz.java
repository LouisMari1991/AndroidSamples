package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;

/**
 * Description:
 * Author：SYNC on 2017-07-02 21:42
 * Contact：289168296@qq.com
 */
public class AlphaPickerQuiz extends Quiz<String> {

  protected AlphaPickerQuiz(String question, String answer, boolean solved) {
    super(question, answer, solved);
  }

  public AlphaPickerQuiz(Parcel in) {
    super(in);
    setAnswer(in.readString());
  }

  @Override public QuizType getType() {
    return QuizType.ALPHA_PICKER;
  }

  @Override public String getStringAnswer() {
    return getAnswer();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(getAnswer());
  }
}
