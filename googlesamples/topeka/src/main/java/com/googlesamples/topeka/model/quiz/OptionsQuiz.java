package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import java.util.Arrays;

/**
 * Description:
 * Author：SYNC on 2017-07-03 22:14
 * Contact：289168296@qq.com
 */
public abstract class OptionsQuiz<T> extends Quiz<int[]> {

  private T[] mOptions;

  public OptionsQuiz(String question, int[] answer, T[] options, boolean solved) {
    super(question, answer, solved);
    mOptions = options;
  }

  public OptionsQuiz(Parcel in) {
    super(in);
    final int[] answer = in.createIntArray();
    setAnswer(answer);
  }

  public T[] getOptions() {
    return mOptions;
  }

  protected void setOptions(T[] options) {
    mOptions = options;
  }

  @Override public boolean isAnswerCorrect(int[] answer) {
    return Arrays.equals(getAnswer(), answer);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    super.writeToParcel(dest, i);
    dest.writeIntArray(getAnswer());
  }

  @SuppressWarnings("RedundantIfStatement") @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OptionsQuiz)) {
      return false;
    }

    OptionsQuiz that = (OptionsQuiz) o;

    if (!Arrays.equals(getAnswer(), ((int[]) that.getAnswer()))) {
      return false;
    }

    if (!Arrays.equals(mOptions, that.mOptions)) {
      return false;
    }

    return true;
  }

  @Override public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Arrays.hashCode(mOptions);
    return result;
  }
}
