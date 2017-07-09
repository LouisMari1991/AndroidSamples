package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import com.googlesamples.topeka.helper.AnswerHelper;
import java.util.Arrays;

/**
 * Description:
 * Author：SYNC on 2017-07-04 23:03
 * Contact：289168296@qq.com
 */
public class ToggleTranslateQuiz extends OptionsQuiz<String[]> {

  private String[] mReadableOptions;

  public ToggleTranslateQuiz(String question, int[] answer, String[][] options, boolean solved) {
    super(question, answer, options, solved);
  }

  public ToggleTranslateQuiz(Parcel in) {
    super(in);
    setAnswer(in.createIntArray());
    setOptions((String[][]) in.readSerializable());
  }

  @Override public QuizType getType() {
    return QuizType.TOGGLE_TRANSLATE;
  }

  @Override public String getStringAnswer() {
    return AnswerHelper.getAnswer(getAnswer(), getReadableOptions());
  }

  public String[] getReadableOptions() {
    // lazily initialize
    if (null == mReadableOptions) {
      final String[][] options = getOptions();
      mReadableOptions = new String[options.length];
      // iterate over the options and create readable pairs
      for (int i = 0; i < options.length; i++) {
        mReadableOptions[i] = createReadablePair(options[i]);
      }
    }
    return mReadableOptions;
  }

  private String createReadablePair(String[] option) {
    // return in "Part one <> Part two"
    return option[0] + " <> " + option[1];
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ToggleTranslateQuiz)) {
      return false;
    }

    ToggleTranslateQuiz that = (ToggleTranslateQuiz) o;

    if (!Arrays.equals(getAnswer(), that.getAnswer())) {
      return false;
    }

    if (!Arrays.deepEquals(getOptions(), that.getOptions())) {
      return false;
    }

    return true;
  }

  @Override public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Arrays.hashCode(getOptions());
    return result;
  }
}
