package com.sync.androidsamples.googlesamples.topeka.topeka.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class Player implements Parcelable{

  protected Player(Parcel in) {
  }

  public static final Creator<Player> CREATOR = new Creator<Player>() {
    @Override public Player createFromParcel(Parcel in) {
      return new Player(in);
    }

    @Override public Player[] newArray(int size) {
      return new Player[size];
    }
  };

  //private final String mFirstName;
  //private final String mLastInitial;


  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
  }
}
