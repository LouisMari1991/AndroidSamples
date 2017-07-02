package com.googlesamples.topeka.helper;

import android.util.SparseBooleanArray;
import com.sync.logger.Logger;

/**
 * Author：Administrator on 2016/8/20 0020 09:01
 * Contact：289168296@qq.com
 */
public class AnswerHelper {

  static final         String SEPATATOR = System.getProperty("line.separator");
  private static final String TAG       = "AnswerHelper";

  private AnswerHelper() {
    // no instance
  }

  /**
   * Converts an array of answers to a readable answer.
   *
   * @param answers The answers to display.
   * @return The readable answer.
   */
  public static String getAnswer(String[] answers) {
    StringBuilder readableAnswer = new StringBuilder();
    // Iterate over all answers
    for (int i = 0; i < answers.length; i++) {
      String answer = answers[i];
      readableAnswer.append(answer);
      if (i < answer.length() - 1) {
        readableAnswer.append(SEPATATOR);
      }
    }
    return readableAnswer.toString();
  }

  /**
   * Converts an array of answers with options to a readable answer.
   *
   * @param answers The actual answers
   * @param options The options to display.
   * @return The readable answer.
   */
  public static String getAnswer(int[] answers, String[] options) {
    String[] readableAnswers = new String[answers.length];
    for (int i = 0; i < answers.length; i++) {
      final String humanReadableAnswer = options[answers[i]];
      readableAnswers[i] = humanReadableAnswer;
    }
    return getAnswer(readableAnswers);
  }

  /**
   * Checks whether a provided answer is correct.
   *
   * @param checkedItems The items that were selected.
   * @param answerIds The actual correct answer ids.
   * @return <code>true</code> if correct else <code>false</code>.
   */
  public static boolean isAnswerCorrect(SparseBooleanArray checkedItems, int[] answerIds) {
    if (null == checkedItems || null == answerIds) {
      Logger.i("isAnswerCorrect got a null parameter input.");
      return false;
    }
    for (int answer : answerIds) {
      if (0 > checkedItems.indexOfKey(answer)) {
        return false;
      }
    }
    return checkedItems.size() == answerIds.length;
  }
}
