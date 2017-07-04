package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import android.os.Parcelable;
import com.googlesamples.topeka.helper.AnswerHelper;
import java.util.Arrays;

/**
 * Description:
 * Author：SYNC on 2017-07-03 22:13
 * Contact：289168296@qq.com
 */
public class FourQuarterQuiz extends OptionsQuiz<String> {

  public static final Parcelable.Creator<FourQuarterQuiz> CREATOR = new Parcelable.Creator<FourQuarterQuiz>() {

    @Override public FourQuarterQuiz createFromParcel(Parcel source) {
      return new FourQuarterQuiz(source);
    }

    @Override public FourQuarterQuiz[] newArray(int size) {
      return new FourQuarterQuiz[size];
    }
  };

  public FourQuarterQuiz(String question, int[] answer, String[] options, boolean solved) {
    super(question, answer, options, solved);
  }

  protected FourQuarterQuiz(Parcel in) {
    super(in);
    String options[] = in.createStringArray();
    setOptions(options);
  }

  @Override public QuizType getType() {
    return QuizType.FOUR_QUARTER;
  }

  @Override public String getStringAnswer() {
    return AnswerHelper.getAnswer(getAnswer(), getOptions());
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    super.writeToParcel(dest, i);
    String[] options = getOptions();
    dest.writeStringArray(options);
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FourQuarterQuiz)) {
      return false;
    }

    FourQuarterQuiz quiz = (FourQuarterQuiz) o;
    final int[] answer = getAnswer();
    final String question = getQuestion();
    if (answer != null ? !Arrays.equals(answer, quiz.getAnswer()) : quiz.getAnswer() != null) {
      return false;
    }
    if (!question.equals(quiz.getQuestion())) {
      return false;
    }

    //noinspection RedundantIfStatement
    if (!Arrays.equals(getOptions(), quiz.getOptions())) {
      return false;
    }
    return true;
  }

  @Override public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Arrays.hashCode(getOptions());
    result = 31 * result + Arrays.hashCode(getAnswer());
    return result;
  }
}
