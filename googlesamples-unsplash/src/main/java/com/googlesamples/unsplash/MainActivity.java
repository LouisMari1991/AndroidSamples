package com.googlesamples.unsplash;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.widget.ProgressBar;
import com.googlesamples.unsplash.model.Photo;
import com.googlesamples.unsplash.ui.TransitionCallback;
import com.googlesamples.unsplash.ui.grid.GridMarginDecoration;
import java.util.ArrayList;

/**
 * Author：Administrator on 2016/8/31 0031 20:33
 * Contact：289168296@qq.com
 */
public class MainActivity extends Activity {

  private static final int PHOTO_COUNT = 12;
  private static final String TAG = "MainActivity";

  private final Transition.TransitionListener sharedExitListener = new TransitionCallback() {
    @Override public void onTransitionEnd(Transition transition) {
      setExitSharedElementCallback(null);
    }
  };

  private RecyclerView grid;
  private ProgressBar empty;
  private ArrayList<Photo> relevantPhotos;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    postponeEnterTransition();
    // Listener to reset shared element exit transition callbacks.
    // 监听 复位 分享 元素 推出 平移 回调
    getWindow().getSharedElementExitTransition().addListener(sharedExitListener);

    grid = (RecyclerView) findViewById(R.id.image_grid);
    empty = (ProgressBar) findViewById(android.R.id.empty);

    setupRecyclerView();

    if (savedInstanceState != null) {
      relevantPhotos = savedInstanceState.getParcelableArrayList(IntentUtil.RELEVANT_PHOTO);
    }
    displayData();
  }

  private void displayData() {
    if (relevantPhotos != null) {

    } else {

    }
  }

  private void populateGrid() {
    //grid.setAdapter(new Ph);
  }



  private void setupRecyclerView() {
    GridLayoutManager gridLayoutManager = (GridLayoutManager) grid.getLayoutManager();
    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
       /* emulating https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0B6Okdz75tqQsck9lUkgxNVZza1U/style_imagery_integration_scale1.png */
        switch (position % 6) {
          case 5:
            return 3;
          case 3:
            return 2;
          default:
            return 1;
        }
      }
    });
    // 设置自定义间隔
    grid.addItemDecoration(new GridMarginDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
    //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
    grid.setHasFixedSize(true);
  }

}
