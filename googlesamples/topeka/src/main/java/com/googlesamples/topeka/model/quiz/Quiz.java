package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Administrator on 2016/8/30 0030 21:58
 * Contact：289168296@qq.com
 */
public class Quiz<A> implements Parcelable{
  protected Quiz(Parcel in) {
  }

  public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
    @Override public Quiz createFromParcel(Parcel in) {
      return new Quiz(in);
    }

    @Override public Quiz[] newArray(int size) {
      return new Quiz[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
  }
}
