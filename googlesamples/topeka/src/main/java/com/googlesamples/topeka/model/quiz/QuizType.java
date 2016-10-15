package com.googlesamples.topeka.model.quiz;

public enum QuizType {

  A("abc",Quiz.class);

  private final String mJsonName;
  private final Class<? extends Quiz> mType;

  QuizType(final String jsonName, final Class<? extends Quiz> type) {
    mJsonName = jsonName;
    mType = type;
  }

  public String getJsonName() {
    return mJsonName;
  }

  public Class<? extends Quiz> getType() {
    return mType;
  }


}