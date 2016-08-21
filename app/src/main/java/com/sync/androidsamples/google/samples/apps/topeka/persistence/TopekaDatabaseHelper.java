package com.sync.androidsamples.google.samples.apps.topeka.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sync.androidsamples.R;
import com.sync.androidsamples.common.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author：Administrator on 2016/8/21 0021 11:30
 * Contact：289168296@qq.com
 */
public class TopekaDatabaseHelper extends SQLiteOpenHelper {

  private static final String TAG = "TopekaDatabaseHelper";
  private static final String DB_NAME = "topeka";
  private static final String DB_SUFFIX = ".db";
  private static final int DB_VERSION = 1;
  private static TopekaDatabaseHelper mInstance;
  private final Resources mResources;

  private TopekaDatabaseHelper(Context context) {
    //prevents external instance creation
    super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
    mResources = context.getResources();
  }

  private static TopekaDatabaseHelper getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new TopekaDatabaseHelper(context);
    }
    return mInstance;
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CategoryTable.CREATE);
    sqLiteDatabase.execSQL(QuizTable.CREATE);
    preFillDatabase(sqLiteDatabase);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    /* no-op */
  }

  private void preFillDatabase(SQLiteDatabase db) {
    try{
      db.beginTransaction();
      try{
        db.setTransactionSuccessful();
        fillCategoriesAndQuizzes(db);
      } finally {
        db.endTransaction();
      }
    } catch (IOException | JSONException e){
      LogUtils.e("preFillDatabase", e);
    }
  }

  private void fillCategoriesAndQuizzes(SQLiteDatabase db) throws JSONException, IOException {
    ContentValues values = new ContentValues(); // reduce, reuse
    JSONArray jsonArray = new JSONArray(readCategoriesFromResource());
    JSONObject category;
    for (int i = 0; i < jsonArray.length(); i++){
      category = jsonArray.getJSONObject(i);
      //final String categoryid = category.getString(JSONA)
    }

  }

  private String readCategoriesFromResource() throws IOException {
    StringBuffer categoriesJson = new StringBuffer();
    InputStream rawCategories = mResources.openRawResource(R.raw.categories);
    BufferedReader reader = new BufferedReader(new InputStreamReader(rawCategories));
    String line;

    while ((line = reader.readLine()) != null) {
      categoriesJson.append(line);
    }
    return categoriesJson.toString();
  }


}





























