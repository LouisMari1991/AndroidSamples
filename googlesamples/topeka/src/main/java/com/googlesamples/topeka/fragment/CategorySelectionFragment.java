package com.googlesamples.topeka.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.activity.QuizActivity;
import com.googlesamples.topeka.adapter.CategoryAdapter;
import com.googlesamples.topeka.helper.TransitionHelper;
import com.googlesamples.topeka.model.Category;
import com.googlesamples.topeka.model.JsonAttributes;
import com.googlesamples.topeka.widget.OffsetDecoration;

/**
 * Author：Administrator on 2016/8/20 0020 17:40
 * Contact：289168296@qq.com
 */
public class CategorySelectionFragment extends Fragment {

  private CategoryAdapter mAdapter;
  private static final int REQUEST_CATEGORY = 0x2300;

  public static CategorySelectionFragment newInstance() {
    return new CategorySelectionFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_categories, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    setUpQuizGrid((RecyclerView) view.findViewById(R.id.categories));
    super.onViewCreated(view, savedInstanceState);
  }

  private void setUpQuizGrid(final RecyclerView categoriesView) {
    final int spacing = getContext().getResources().getDimensionPixelSize(R.dimen.spacing_nano);
    categoriesView.addItemDecoration(new OffsetDecoration(spacing));
    mAdapter = new CategoryAdapter(getActivity());
    mAdapter.setOnitemClicklistener(new CategoryAdapter.OnItemClickListener() {
      @Override public void onCLick(View view, int position) {
        Activity activity = getActivity();
        startQuizActivityWithTransition(activity, view.findViewById(R.id.category_title),
            mAdapter.getItem(position));
      }
    });
    categoriesView.setAdapter(mAdapter);
    categoriesView.getViewTreeObserver()
        .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
          @Override public boolean onPreDraw() {
            categoriesView.getViewTreeObserver().removeOnPreDrawListener(this);
            getActivity().supportStartPostponedEnterTransition();
            return true;
          }
        });
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CATEGORY && resultCode == R.id.solved) {
      mAdapter.notifyItemChanged(data.getStringExtra(JsonAttributes.ID));
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void startQuizActivityWithTransition(Activity activity, View toolbar, Category category) {
    final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
        new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
    @SuppressWarnings("unchecked") ActivityOptionsCompat sceneTransitionAnimation =
        ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);

    // Start the activity with the participants, animating from one to the other.
    // 开始的活动参与者,动画从一个到另一个。
    final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
    Intent startIntent = QuizActivity.getStartIntent(activity, category);
    ActivityCompat.startActivityForResult(activity, startIntent, REQUEST_CATEGORY,
        transitionBundle);
  }
}
