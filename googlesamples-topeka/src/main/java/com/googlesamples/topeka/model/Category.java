package com.googlesamples.topeka.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.googlesamples.topeka.helper.ParcelableHelper;
import com.googlesamples.topeka.model.quiz.Quiz;
import java.util.ArrayList;
import java.util.List;

/**
 * Author：Administrator on 2016/8/30 0030 21:50
 * Contact：289168296@qq.com
 */
public class Category implements Parcelable{

  private static final String TAG = "Category";

  public static final Creator<Category> CREATOR = new Creator<Category>() {
    @Override public Category createFromParcel(Parcel in) {
      return new Category(in);
    }

    @Override public Category[] newArray(int size) {
      return new Category[size];
    }
  };

  private static final int SCORE = 8;
  private static final int NO_SCORE = 0;
  private final String mName;
  private final String mId;
  private final Theme mTheme;
  private final int[] mScores;
  private List<Quiz> mQuizzes;
  private boolean mSolved;

  public Category(@NonNull String name, @NonNull String id, @NonNull Theme theme,
      @NonNull List<Quiz> quizzes, boolean solved) {
    mName = name;
    mId = id;
    mTheme = theme;
    mQuizzes = quizzes;
    mScores = new int[quizzes.size()];
    mSolved = solved;
  }

  protected Category(Parcel in) {
    mName = in.readString();
    mId = in.readString();
    mTheme = Theme.values()[in.readInt()];
    mQuizzes = new ArrayList<>();
    in.readTypedList(mQuizzes, Quiz.CREATOR);
    mScores = in.createIntArray();
    mSolved = ParcelableHelper.readBoolean(in);
  }

  @NonNull
  public List<Quiz> getQuizzes() {
    return mQuizzes;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mName);
    dest.writeString(mId);
    dest.writeInt(mTheme.ordinal());
    dest.writeTypedList(getQuizzes());
    dest.writeIntArray(mScores);
    ParcelableHelper.writeBoolean(dest, mSolved);
  }
}
