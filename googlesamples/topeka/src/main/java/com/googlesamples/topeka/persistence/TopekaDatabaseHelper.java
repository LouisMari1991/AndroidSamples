package com.googlesamples.topeka.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.model.Category;
import com.googlesamples.topeka.model.JsonAttributes;
import com.sync.logger.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Database for storing and retrieving info for category and quizzes
 * 数据库存储和检索信息的类别和测验
 *
 * Author：Administrator on 2016/8/21 0021 11:30
 * Contact：289168296@qq.com
 */
public class TopekaDatabaseHelper extends SQLiteOpenHelper {

  private static final String TAG = "TopekaDatabaseHelper";
  private static final String DB_NAME = "topeka";
  private static final String DB_SUFFIX = ".db";
  private static final int DB_VERSION = 1;
  private static List<Category> mCategories;
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

  /**
   * Gets all categories their quizzes.
   * 得到所有类别测验。
   *
   * @param context The context this is running in.
   * @param fromDatabase <code>true</code> if a data refresh is needed, else <code>false</code>
   * @return All categories stored in the database.
   */
  public static List<Category> getCategories(Context context, boolean fromDatabase) {
    if (mCategories == null || fromDatabase) {
      mCategories = loadCategories(context);
    }
    return mCategories;
  }

  private static List<Category> loadCategories(Context context) {
    //Cursor data = TopekaDatabaseHelper.get
    return null;
  }

  private static Cursor getCategoryCursor(Context context) {
    //SQLiteDatabase readableDatabase = getRadasd
    return null;
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
    SQLiteDatabase writableDatabase = getWritableDatabase(context);
    writableDatabase.delete(CategoryTable.NAME, null, null);
    writableDatabase.delete(QuizTable.NAME, null, null);
    getInstance(context).preFillDatabase(writableDatabase);
  }

  private static SQLiteDatabase getReadableDatabase(Context context) {
    return getInstance(context).getReadableDatabase();
  }

  public static SQLiteDatabase getWritableDatabase(Context context) {
    return getInstance(context).getWritableDatabase();
  }

  private void preFillDatabase(SQLiteDatabase db) {
    try {
      db.beginTransaction();
      try {
        fillCategoriesAndQuizzes(db);
        db.setTransactionSuccessful();
      } finally {
        db.endTransaction();
      }
    } catch (IOException | JSONException e) {
      Logger.e("preFillDatabase", e);
    }
  }

  private void fillCategoriesAndQuizzes(SQLiteDatabase db) throws JSONException, IOException {
    ContentValues values = new ContentValues(); // reduce, reuse
    JSONArray jsonArray = new JSONArray(readCategoriesFromResource());
    JSONObject category;
    for (int i = 0; i < jsonArray.length(); i++) {
      category = jsonArray.getJSONObject(i);
      final String categoryid = category.getString(JsonAttributes.ID);
    }
  }

  /**
   * Read categories.json properties file.
   *
   * @return properties file to String.
   * @throws IOException
   */
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
      String categoryid) throws JSONException {
    values.clear();
  }
}





























