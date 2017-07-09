package com.googlesamples.topeka.model.quiz;

import android.os.Parcel;
import com.googlesamples.topeka.helper.AnswerHelper;
import java.util.Arrays;

/**
 * Description:
 * Author：SYNC on 2017-07-02 22:15
 * Contact：289168296@qq.com
 */
public class FillTwoBlanksQuiz extends Quiz<String[]> {

  public FillTwoBlanksQuiz(String question, String[] answer, boolean solved) {
    super(question, answer, solved);
  }

  public FillTwoBlanksQuiz(Parcel in) {
    super(in);
    String[] answer = in.createStringArray();
    setAnswer(answer);
  }

  @Override public QuizType getType() {
    return QuizType.FILL_TWO_BLANKS;
  }

  @Override public String getStringAnswer() {
    return AnswerHelper.getAnswer(getAnswer());
  }

  @Override public boolean isAnswerCorrect(String[] answer) {
    return super.isAnswerCorrect(answer);
  }

  @Override public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Arrays.hashCode(getAnswer());
    return result;
  }
}
