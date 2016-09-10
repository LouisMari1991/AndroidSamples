package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This abstract provider general structure for quizzes.
 *
 * Author：Administrator on 2016/8/30 0030 21:58
 * Contact：289168296@qq.com
 */
public class Quiz<A> implements Parcelable{

  private static final String TAG = "Quiz";

  public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
    @Override public Quiz createFromParcel(Parcel in) {
      return new Quiz(in);
    }

    @Override public Quiz[] newArray(int size) {
      return new Quiz[size];
    }
  };

  /**
   * 标识已经回答过，不保证正确。
   * Flag indicating whether this quiz has already been solved.
   * It does not give information whether the solution was correct or not.
   */
  private boolean mSolved;

  public boolean isSolved() {
    return mSolved;
  }

  protected Quiz(Parcel in) {

  }




  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
  }
}
