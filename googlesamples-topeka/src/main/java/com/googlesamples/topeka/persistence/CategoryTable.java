package com.googlesamples.topeka.persistence;

import android.provider.BaseColumns;

/**
 * Author：Administrator on 2016/8/21 0021 11:31
 * Contact：289168296@qq.com
 */
public interface CategoryTable {

  String NAME = "category";

  String COLUMN_ID = BaseColumns._ID;
  String COLUMN_NAME = "name";
  String COLUMN_THEME = "theme";
  String COLUMN_SCORES = "score";
  String COLUMN_SOLVED = "solved";

  String[] PROJECTION = new String[] {
      COLUMN_ID, COLUMN_NAME, COLUMN_THEME, COLUMN_SCORES, COLUMN_SOLVED
  };

  String CREATE = "CREATE TABLE " + NAME + " ("
      + COLUMN_ID + " TEXT PRIMARY KEY, "
      + COLUMN_NAME + " TEXT NOT NULL, "
      + COLUMN_THEME + " TEXT NOT NULL, "
      + COLUMN_SCORES + " TEXT NOT NULL, "
      + COLUMN_SOLVED + " TEXT);";
}
