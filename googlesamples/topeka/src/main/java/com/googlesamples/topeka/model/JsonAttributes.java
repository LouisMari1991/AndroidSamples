package com.googlesamples.topeka.model;

/**
 * Author：Administrator on 2016/8/21 0021 15:56
 * Contact：289168296@qq.com
 */
public interface JsonAttributes {

  String ANSWER = "answer";
  String END = "end";
  String ID = "id";
  String MAX = "max";
  String MIN = "min";
  String NAME = "name";
  String OPTIONS = "options";
  String QUESTION = "question";
  String QUIZZES = "quizzes";
  String START = "start";
  String STEP = "step";
  String THEME = "theme";
  String TYPE = "type";
  String SCORES = "scores";
  String SOLVED = "solved"; //解决

  /**
   * 问题类型
   * 2016/8/21 0021 18:42
   */
  interface QuizType {
    String ALPHA_PICKER = "alpha-picker"; //α选择器
    String FILL_BLANK = "fill-blank"; // 填满空白
    String FILL_TWO_BLANKS = "fill-two-blanks";//填满两个空白
    String FOUR_QUARTER = "four-quarter"; // 四个季度
    String MULTI_SELECT = "multi-select"; // 多选
    String PICKER = "picker";
    String SINGLE_SELECT = "single-select";
    String SINGLE_SELECT_ITEM = "single-select-item";
    String TOGGLE_TRANSLATE = "toggle-translate"; // 切换翻译
    String TRUE_FALSE = "true-false";
  }
}
