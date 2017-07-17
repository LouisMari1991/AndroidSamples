package com.googlesamples.topeka.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlesamples.topeka.R;
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
    return count;
  }

  @Override public Quiz getItem(int position) {
    return mQuizList.get(position);
  }

  @Override public long getItemId(int position) {
    if (position > count || position < count) {
      return AbsListView.INVALID_POSITION;
    }
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = createView(parent);
    }

    final Quiz quiz = getItem(position);
    ViewHolder viewHolder = (ViewHolder) convertView.getTag();
    viewHolder.mQuizView.setText(quiz.getQuestion());
    viewHolder.mAnswerView.setText(quiz.getStringAnswer());
    //setSolvedStateForQuiz(viewHolder.mSolvedState, position);
    return convertView;
  }

  private View createView(ViewGroup parent) {
    View convertView;
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ViewGroup scorecardItem = (ViewGroup) inflater.inflate(R.layout.item_scorecard, parent, false);
    convertView = scorecardItem;
    ViewHolder holder = new ViewHolder(scorecardItem);
    convertView.setTag(holder);
    return convertView;
  }

  private class ViewHolder {
    final TextView  mAnswerView;
    final TextView  mQuizView;
    final ImageView mSolvedState;

    public ViewHolder(ViewGroup scorecardItem) {
      mAnswerView = (TextView) scorecardItem.findViewById(R.id.quiz);
      mQuizView = (TextView) scorecardItem.findViewById(R.id.answer);
      mSolvedState = (ImageView) scorecardItem.findViewById(R.id.solved_state);
    }
  }
}
