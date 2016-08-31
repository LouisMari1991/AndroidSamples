package com.googlesamples.topeka.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.model.JsonAttributes;
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

  public static int getScore(Context context) {
    //final List<Ca>
    return 0;
  }

  public static void reset(Context context) {
    SQLiteDatabase writebleDatable = getWritableDatabase(context);
    writebleDatable.delete(CategoryTable.NAME, null, null);
    writebleDatable.delete(CategoryTable.NAME, null, null);
    getInstance(context).preFillDatabase(writebleDatable);
  }

  public static SQLiteDatabase getWritableDatabase(Context context) {
    return getInstance(context).getWritableDatabase();
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
      Log.e(TAG, "preFillDatabase", e);
    }
  }

  private void fillCategoriesAndQuizzes(SQLiteDatabase db) throws JSONException, IOException {
    ContentValues values = new ContentValues(); // reduce, reuse
    JSONArray jsonArray = new JSONArray(readCategoriesFromResource());
    JSONObject category;
    for (int i = 0; i < jsonArray.length(); i++){
      category = jsonArray.getJSONObject(i);
      final String categoryid = category.getString(JsonAttributes.ID);

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

  private void fillCategory(SQLiteDatabase db, ContentValues values, JSONObject category,
      String categoryid) throws JSONException{
      values.clear();

  }



}





























