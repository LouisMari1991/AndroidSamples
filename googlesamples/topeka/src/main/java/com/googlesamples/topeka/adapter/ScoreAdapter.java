package com.googlesamples.topeka.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
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
    setSolvedStateForQuiz(viewHolder.mSolvedState, position);
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

  private void setSolvedStateForQuiz(ImageView solvedState, int position) {
    final Context context = solvedState.getContext();
    final Drawable tintedImage;
    if (mCategory.isSolvedCorrectly(getItem(position))) {
      tintedImage = getSuccessIcon(context);
    } else {
      tintedImage = getFailedIcon(context);
    }
    solvedState.setImageDrawable(tintedImage);
  }

  private Drawable getSuccessIcon(Context context) {
    if (null == mSuccessIcon) {
      mSuccessIcon = loadAndTint(context, R.drawable.ic_tick, R.color.theme_green_primary);
    }
    return mSuccessIcon;
  }

  private Drawable getFailedIcon(Context context) {
    if (null == mFailedIcon) {
      mFailedIcon = loadAndTint(context, R.drawable.ic_cross, R.color.theme_red_primary);
    }
    return mFailedIcon;
  }

  private Drawable loadAndTint(Context context, @DrawableRes int drawableId, @ColorRes int tintColor) {
    Drawable imageDrawable = ContextCompat.getDrawable(context, drawableId);
    if (imageDrawable == null) {
      throw new IllegalArgumentException("The drawable with id " + drawableId + " does not exist");
    }
    DrawableCompat.setTint(DrawableCompat.wrap(imageDrawable), ContextCompat.getColor(context, tintColor));
    return imageDrawable;
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
