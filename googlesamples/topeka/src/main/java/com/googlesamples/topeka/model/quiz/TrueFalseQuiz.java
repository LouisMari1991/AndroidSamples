package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import com.googlesamples.topeka.helper.ParcelableHelper;

/**
 * Description:
 * Author：SYNC on 2017-07-04 23:05
 * Contact：289168296@qq.com
 */
public class TrueFalseQuiz extends Quiz<Boolean> {
  protected TrueFalseQuiz(String question, Boolean answer, boolean solved) {
    super(question, answer, solved);
  }

  protected TrueFalseQuiz(Parcel in) {
    super(in);
    setAnswer(ParcelableHelper.readBoolean(in));
  }

  @Override public QuizType getType() {
    return null;
  }

  @Override public String getStringAnswer() {
    return getAnswer().toString();
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    super.writeToParcel(dest, i);
    ParcelableHelper.writeBoolean(dest, getAnswer());
  }
}
