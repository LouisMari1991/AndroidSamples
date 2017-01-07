package com.googlesamples.unsplash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.googlesamples.unsplash.data.model.Photo;
import com.googlesamples.unsplash.ui.DetailSharedElementEnterCallback;
import com.googlesamples.unsplash.ui.pager.DetailViewPagerAdapter;
import java.util.ArrayList;

/**
 * Author：Administrator on 2016/8/31 0031 20:34
 * Contact：289168296@qq.com
 */
public class DetailActivity extends Activity {

  private static final String STATE_INITIAL_ITEM = "initial";
  private ViewPager viewPager;
  private int initialItem;
  private final View.OnClickListener navigationOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      finishAfterTransition();
    }
  };
  private DetailSharedElementEnterCallback sharedElementCallback;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_detail);

    postponeEnterTransition();

    // 设置Activity入场动画
    TransitionSet transitions = new TransitionSet();
    Slide slide = new Slide(Gravity.BOTTOM);
    slide.setInterpolator(
        AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
    slide.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
    transitions.addTransition(slide);
    transitions.addTransition(new Fade());
    getWindow().setEnterTransition(transitions);

    // 设置共享元素动画
    Intent intent = getIntent();
    sharedElementCallback = new DetailSharedElementEnterCallback(intent);
    setEnterSharedElementCallback(sharedElementCallback);
    initialItem = intent.getIntExtra(IntentUtil.SELECTED_ITEM_POSITION, 0);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setNavigationOnClickListener(navigationOnClickListener);

    super.onCreate(savedInstanceState);
  }

  private void setUpViewPager(ArrayList<Photo> photos) {
    viewPager = (ViewPager) findViewById(R.id.pager);
    viewPager.setAdapter(new DetailViewPagerAdapter(this,photos, sharedElementCallback));

  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putInt(STATE_INITIAL_ITEM, initialItem);
    super.onSaveInstanceState(outState);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    initialItem = savedInstanceState.getInt(STATE_INITIAL_ITEM, 0);
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override public void onBackPressed() {
    setActivityResult();
    super.onBackPressed();
  }

  @Override public void finishAfterTransition() {
    setActivityResult();
    super.finishAfterTransition();
  }

  private void setActivityResult() {
    if (initialItem == viewPager.getCurrentItem()) {
      setResult(RESULT_OK);
      return;
    }
    Intent intent = new Intent();
    intent.putExtra(IntentUtil.SELECTED_ITEM_POSITION, viewPager.getCurrentItem());
    setResult(RESULT_OK, intent);
  }
}
