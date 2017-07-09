package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;

/**
 * Description:
 * Author：SYNC on 2017-07-04 22:33
 * Contact：289168296@qq.com
 */
public class PickerQuiz extends Quiz<Integer> {

  private final int mMin;
  private final int mMax;
  private final int mStep;

  public PickerQuiz(String question, Integer answer, int min, int max, int step, boolean solved) {
    super(question, answer, solved);
    mMin = min;
    mMax = max;
    mStep = step;
  }

  public PickerQuiz(Parcel in) {
    super(in);
    setAnswer(in.readInt());
    mMin = in.readInt();
    mMax = in.readInt();
    mStep = in.readInt();
  }

  public int getMin() {
    return mMin;
  }

  public int getMax() {
    return mMax;
  }

  public int getStep() {
    return mStep;
  }

  @Override public QuizType getType() {
    return QuizType.PICKER;
  }

  @Override public String getStringAnswer() {
    return getAnswer().toString();
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    super.writeToParcel(dest, i);
    dest.writeInt(getAnswer());
    dest.writeInt(mMin);
    dest.writeInt(mMax);
    dest.writeInt(mStep);
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PickerQuiz)) {
      return false;
    }
    //noinspection EqualsBetweenInconvertibleTypes
    if (!super.equals(o)) {
      return false;
    }
    PickerQuiz that = (PickerQuiz) o;
    if (mMin != that.mMin) {
      return false;
    }
    //noinspection SimplifiableIfStatement
    if (mMax != that.mMax) {
      return false;
    }
    return mStep == that.mStep;
  }

  @Override public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + mMin;
    result = 31 * result + mMax;
    result = 31 * result + mStep;
    return result;
  }
}
