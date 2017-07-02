package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;

/**
 * Description:
 * Author：SYNC on 2017-07-02 21:55
 * Contact：289168296@qq.com
 */
public class FillBlankQuiz extends Quiz<String> {

  private final String mStart;
  private final String mEnd;

  protected FillBlankQuiz(String question, String answer, String start, String end, boolean solved) {
    super(question, answer, solved);
    mStart = start;
    mEnd = end;
  }

  protected FillBlankQuiz(Parcel in) {
    super(in);
    setAnswer(in.readString());
    mStart = in.readString();
    mEnd = in.readString();
  }

  @Override public String getStringAnswer() {
    return getAnswer();
  }

  public String getStart() {
    return mStart;
  }

  public String getEnd() {
    return mEnd;
  }

  @Override public QuizType getType() {
    return QuizType.FILL_BLANK;
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    super.writeToParcel(dest, i);
    dest.writeString(getAnswer());
    dest.writeString(mStart);
    dest.writeString(mEnd);
  }
}
