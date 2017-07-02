package com.googlesamples.topeka.model.quiz;

import com.googlesamples.topeka.model.JsonAttributes;

public enum QuizType {

  ALPHA_PICKER(JsonAttributes.QuizType.ALPHA_PICKER, AlphaPickerQuiz.class),
  FILL_BLANK(JsonAttributes.QuizType.FILL_BLANK, FillBlankQuiz.class),
  FILL_TWO_BLANKS(JsonAttributes.QuizType.FILL_TWO_BLANKS, FillTwoBlanksQuiz.class);


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