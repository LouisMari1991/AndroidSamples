package com.googlesamples.topeka.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.helper.JsonHelper;
import com.googlesamples.topeka.model.Category;
import com.googlesamples.topeka.model.JsonAttributes;
import com.googlesamples.topeka.model.Theme;
import com.googlesamples.topeka.model.quiz.Quiz;
import com.sync.logger.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

  private static final String TAG        = "TopekaDatabaseHelper";
  private static final String DB_NAME    = "topeka";
  private static final String DB_SUFFIX  = ".db";
  private static final int    DB_VERSION = 1;
  private static List<Category>       mCategories;
  private static TopekaDatabaseHelper mInstance;
  private final  Resources            mResources;

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
    Cursor data = TopekaDatabaseHelper.getCategoryCursor(context);
    List<Category> tempCategories = new ArrayList<Category>(data.getCount());
    final SQLiteDatabase readableDatabase = TopekaDatabaseHelper.getReadableDatabase(context);
    do {
      final Category category = getCategory(data, readableDatabase);
      tempCategories.add(category);
    } while (data.moveToNext());
    return tempCategories;
  }

  private static Cursor getCategoryCursor(Context context) {
    SQLiteDatabase readableDatabase = getReadableDatabase(context);
    Cursor data = readableDatabase.query(CategoryTable.NAME, CategoryTable.PROJECTION, null, null, null, null, null);
    data.moveToFirst();
    return data;
  }

  private static Category getCategory(Cursor cursor, SQLiteDatabase readableDatabase) {
    // "magic number" based on CategoryTable#PROJECTION
    final String id = cursor.getString(0);
    final String name = cursor.getString(1);
    final String themeName = cursor.getString(2);
    final Theme theme = Theme.valueOf(themeName);
    final String isSolved = cursor.getString(3);
    final boolean solved = getBooleanFromDatabase(isSolved);
    final int[] scores = JsonHelper.jsonArrayToIntArray(cursor.getString(4));

    final List<Quiz> quizzes = getQuizzes(id, readableDatabase);
    return new Category(name, id, theme, quizzes, scores, solved);
  }

  private static List<Quiz> getQuizzes(final String categoryId, SQLiteDatabase database) {
    final List<Quiz> quizzes = new ArrayList<>();
    final Cursor cursor = database.query(QuizTable.NAME, QuizTable.PROJECTION, QuizTable.FK_CATEGORY + " LIKE ?",
        new String[] { categoryId }, null, null, null);
    cursor.moveToFirst();
    do {
      //quizzes.add();
    } while (cursor.moveToNext());
    return null;
  }

  private static boolean getBooleanFromDatabase(String isSolved) {
    // json stores booleans as true/false string, whereas SQLite stores theme as 0/1 values.
    return null != isSolved && isSolved.length() == 1 && Integer.valueOf(isSolved) == 1;
  }

  /**
   * Looks for a category with a given id.
   *
   * @param context The context this is tunning in.
   * @param categoryId Id of the category to look for.
   * @return The found categoty.
   */
  public static Category getCategoryWith(Context context, String categoryId) {
    SQLiteDatabase readableDatabase = getReadableDatabase(context);
    String[] selectionArgs = { categoryId };
    Cursor data = readableDatabase.query(CategoryTable.NAME, CategoryTable.PROJECTION, CategoryTable.COLUMN_ID + "=?",
        selectionArgs, null, null, null);
    data.moveToFirst();
    return getCategory(data, readableDatabase);
  }

  /**
   * Score!
   *
   * @param context The context this is running in.
   * @return The socre over all Categories
   */
  public static int getScore(Context context) {
    final List<Category> categories = getCategories(context, false);
    int score = 0;
    for (Category cat : categories) {
      score += cat.getScore();
    }
    return score;
  }

  /**
   * Updates values for category.
   *
   * @param context The context this is running in.
   * @param category The category to update.
   */
  public static void updateCategory(Context context, Category category) {
    if (mCategories != null && mCategories.contains(category)) {
      final int location = mCategories.indexOf(category);
      mCategories.remove(location);
      mCategories.add(location, category);
    }
    SQLiteDatabase writableDatabase = getWritableDatabase(context);
    ContentValues categoryValues = createContentValuesFor(category);
    writableDatabase.update(CategoryTable.NAME, categoryValues, CategoryTable.COLUMN_ID + "=?",
        new String[] { category.getId() });
    final List<Quiz> quizzes = category.getQuizzes();
    updateQuizzes(writableDatabase, quizzes);
  }

  private static ContentValues createContentValuesFor(Category category) {
    return null;
  }

  /**
   * Updates a list of given quizzes.
   *
   * @param writableDatabase The datable to write the quizzes to.
   * @param quizzes The quizzes to write.
   */
  private static void updateQuizzes(SQLiteDatabase writableDatabase, List<Quiz> quizzes) {
    Quiz quiz;
    ContentValues quizValues = new ContentValues();
    String[] quizArgs = new String[1];
    for (int i = 0; i < quizzes.size(); i++) {
      quiz = quizzes.get(i);
      quizValues.clear();
      quizValues.put(QuizTable.COLUMN_SOLVED, quiz.isSolved());

      quizArgs[0] = quiz.getQuestion();
      writableDatabase.update(QuizTable.NAME, quizValues, QuizTable.COLUMN_QUESTION + "=?", quizArgs);
    }
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CategoryTable.CREATE);
    sqLiteDatabase.execSQL(QuizTable.CREATE);
    preFillDatabase(sqLiteDatabase);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    /* no-op */
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

  private void fillCategory(SQLiteDatabase db, ContentValues values, JSONObject category, String categoryid)
      throws JSONException {
    values.clear();
  }
}





























