package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import com.googlesamples.topeka.helper.AnswerHelper;

/**
 * Description:
 * Author：SYNC on 2017-07-04 22:57
 * Contact：289168296@qq.com
 */
public class SelectItemQuiz extends OptionsQuiz<String> {
  protected SelectItemQuiz(String question, int[] answer, String[] options, boolean solved) {
    super(question, answer, options, solved);
  }

  protected SelectItemQuiz(Parcel in) {
    super(in);
    String[] options = in.createStringArray();
    setOptions(options);
  }

  @Override public QuizType getType() {
    return null;
  }

  @Override public String getStringAnswer() {
    return AnswerHelper.getAnswer(getAnswer(), getOptions());
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    super.writeToParcel(dest, i);
    dest.writeStringArray(getOptions());
  }
}
