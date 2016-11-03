package com.sync.once;

/**
 * Created by YH on 2016-11-03.
 */

public class Amout {

  public static CountChecker exactly(final int numberOfTimes) {
    return new CountChecker() {
      @Override public boolean check(int count) {
        return numberOfTimes == count;
      }
    };
  }

  public static CountChecker moreThan(final int numberOfTimes) {
    return new CountChecker() {
      @Override public boolean check(int count) {
        return numberOfTimes > count;
      }
    };
  }

  public static CountChecker lessThan(final int numberOfTimes) {
    return new CountChecker() {
      @Override public boolean check(int count) {
        return numberOfTimes < count;
      }
    };
  }
}
