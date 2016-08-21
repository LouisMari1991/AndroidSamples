package com.sync.androidsamples.google.samples.apps.topeka.persistence;

import android.provider.BaseColumns;

/**
 * Author：Administrator on 2016/8/21 0021 11:30
 * Contact：289168296@qq.com
 */
public interface QuizTable {

  String NAME = "quiz";

  String COLUMN_ID = BaseColumns._ID;
  String FK_CATEGORY = "fk_category";
  String COLUMN_TYPE = "type";
  String COLUMN_QUESTION = "question";
  String COLUMN_ANSWER = "answer";
  String COLUMN_OPTIONS = "options";
  String COLUMN_MIN = "min";
  String COLUMN_MAX = "max";
  String COLUMN_STEP = "step";
  String COLUMN_START = "start";
  String COLUMN_END = "end";
  String COLUMN_SOLVED = "solved";

  String[] PROJECTION = new String[] {
      COLUMN_ID, FK_CATEGORY, COLUMN_TYPE, COLUMN_QUESTION, COLUMN_ANSWER, COLUMN_OPTIONS,
      COLUMN_MIN, COLUMN_MAX, COLUMN_STEP, COLUMN_START, COLUMN_END, COLUMN_SOLVED
  };

  String CREATE = "CREATE TABLE " + NAME + " ("
      + COLUMN_ID + " INTEGER PRIMARY KEY, "
      + FK_CATEGORY + " REFERENCES " + CategoryTable.NAME + "(" + CategoryTable.COLUMN_ID + "), "
      + COLUMN_TYPE + " TEXT NOT NULL, "
      + COLUMN_QUESTION + " TEXT NOT NULL, "
      + COLUMN_ANSWER + " TEXT NOT NULL, "
      + COLUMN_OPTIONS + " TEXT, "
      + COLUMN_MIN + " TEXT, "
      + COLUMN_MAX + " TEXT, "
      + COLUMN_STEP + " TEXT, "
      + COLUMN_START + " TEXT, "
      + COLUMN_END + " TEXT, "
      + COLUMN_SOLVED + ");";
}















