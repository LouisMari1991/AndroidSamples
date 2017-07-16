package com.googlesamples.topeka.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.googlesamples.topeka.model.Category;
import com.googlesamples.topeka.model.quiz.Quiz;
import java.util.List;

/**
 * Description:
 * Author：SYNC on 2017-07-16 21:50
 * Contact：289168296@qq.com
 */
public class ScoreAdapter extends BaseAdapter {

  private final Category   mCategory;
  private final int        count;
  private final List<Quiz> mQuizList;

  private Drawable mSuccessIcon;
  private Drawable mFailedIcon;

  public ScoreAdapter(Category category) {
    mCategory = category;
    mQuizList = category.getQuizzes();
    count = mQuizList.size();
  }

  @Override public int getCount() {
    return 0;
  }

  @Override public Object getItem(int position) {
    return null;
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    return null;
  }
}
