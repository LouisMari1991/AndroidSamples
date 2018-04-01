package com.googlesamples.unsplash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import com.googlesamples.unsplash.data.UnsplashService;
import com.googlesamples.unsplash.data.model.Photo;
import com.googlesamples.unsplash.databinding.PhotoItemBinding;
import com.googlesamples.unsplash.ui.DetailSharedElementEnterCallback;
import com.googlesamples.unsplash.ui.TransitionCallback;
import com.googlesamples.unsplash.ui.grid.GridMarginDecoration;
import com.googlesamples.unsplash.ui.grid.OnItemSelectedListener;
import com.googlesamples.unsplash.ui.grid.PhotoAdapter;
import com.googlesamples.unsplash.ui.grid.PhotoViewHolder;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    grid = findViewById(R.id.image_grid);
    empty = findViewById(android.R.id.empty);

    setupRecyclerView();

    if (savedInstanceState != null) {
      relevantPhotos = savedInstanceState.getParcelableArrayList(IntentUtil.RELEVANT_PHOTO);
    }
    displayData();
  }

  private void displayData() {
    if (relevantPhotos != null) {
      populateGrid();
    } else {
      UnsplashService unsplashApi = new RestAdapter.Builder()
          .setEndpoint(UnsplashService.ENDPOINT)
          .build()
          .create(UnsplashService.class);
      unsplashApi.getFeed(new Callback<List<Photo>>() {
        @Override public void success(List<Photo> photos, Response response) {
          // the first items not interesting to us, get the last <n>
          relevantPhotos = new ArrayList<>(photos.subList(photos.size() - PHOTO_COUNT,
              photos.size()));
          populateGrid();
        }

        @Override public void failure(RetrofitError error) {
          Log.e(TAG, "Error retrieving Unsplash feed:", error);
        }
      });
    }
  }

  private void populateGrid() {
    grid.setAdapter(new PhotoAdapter(this, relevantPhotos));
    grid.addOnItemTouchListener(new OnItemSelectedListener(MainActivity.this) {
      @Override public void onItemSelected(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof PhotoViewHolder)) {
          return;
        }
        PhotoItemBinding binding = ((PhotoViewHolder) holder).getBinding();
        final Intent intent =
            getDetailActivityStartIntent(MainActivity.this, relevantPhotos, position, binding);
        final ActivityOptions activityOptions = getActivityOptions(binding);
        MainActivity.this.startActivityForResult(intent, IntentUtil.REQUEST_CODE,
            activityOptions.toBundle());
      }
    });
    empty.setVisibility(View.GONE);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putParcelableArrayList(IntentUtil.RELEVANT_PHOTO, relevantPhotos);
    super.onSaveInstanceState(outState);
  }

  /**
   * 当返回到activityA的时候，要去获取activityB返回的信息，
   * 可以在onActivityReenter(int requestCode, Intent data)方法里面获取，
   * 比如这里获取到返回的position信息
   *
   * @author YH
   * @time 2016-11-29 11:47
   */
  @Override public void onActivityReenter(int resultCode, Intent data) {
    postponeEnterTransition();
    // Start the postponed transition when the recycler view is ready to be drawn.
    // 延时启动动画， 当Recycler view 已经当好了
    // 注册一个回调函数，当一个视图树将要绘制时调用这个回调函数。
    grid.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override public boolean onPreDraw() {
        grid.getViewTreeObserver().removeOnPreDrawListener(this);
        startPostponedEnterTransition();
        return true;
      }
    });

    if (data == null) {
      return;
    }

    // 获取DetailActivity返回的RecyclerView的position
    final int selectedItem = data.getIntExtra(IntentUtil.SELECTED_ITEM_POSITION, 0);
    // RecyclerView滑动到对应的position
    grid.scrollToPosition(selectedItem);

    PhotoViewHolder holder = (PhotoViewHolder) grid.findViewHolderForAdapterPosition(selectedItem);

    if (holder == null) {
      Log.w(TAG, "onActivityReenter: Holder is null, remapping cancelled.");
      return;
    }

    DetailSharedElementEnterCallback callback = new DetailSharedElementEnterCallback(getIntent());
    //callback.setBin

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
    grid.addItemDecoration(
        new GridMarginDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
    //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
    grid.setHasFixedSize(true);
  }

  @NonNull
  private static Intent getDetailActivityStartIntent(Activity host, ArrayList<Photo> photos,
      int position, PhotoItemBinding binding) {

    final Intent intent = new Intent(host, DetailActivity.class);
    intent.setAction(Intent.ACTION_VIEW);
    intent.putParcelableArrayListExtra(IntentUtil.PHOTO, photos);
    intent.putExtra(IntentUtil.SELECTED_ITEM_POSITION, position);
    intent.putExtra(IntentUtil.FONT_SIZE, binding.author.getTextSize());
    intent.putExtra(IntentUtil.PADDING,
        new Rect(binding.author.getPaddingLeft(),
            binding.author.getPaddingTop(),
            binding.author.getPaddingRight(),
            binding.author.getPaddingBottom()));
    intent.putExtra(IntentUtil.TEXT_COLOR, binding.author.getCurrentTextColor());
    return intent;
  }

  private ActivityOptions getActivityOptions(PhotoItemBinding binding) {
    Pair authorPair = Pair.create(binding.author, binding.author.getTransitionName());
    Pair photoPair = Pair.create(binding.author, binding.author.getTransitionName());
    View decorView = getWindow().getDecorView();
    View statusBackground = decorView.findViewById(android.R.id.statusBarBackground);
    View navBackground = decorView.findViewById(android.R.id.navigationBarBackground);
    Pair statusPair = Pair.create(statusBackground, statusBackground.getTransitionName());

    final ActivityOptions options;
    if (navBackground == null) {
      options =
          ActivityOptions.makeSceneTransitionAnimation(this, authorPair, photoPair, statusPair);
    } else {
      Pair navPair = Pair.create(navBackground, navBackground.getTransitionName());
      options =
          ActivityOptions.makeSceneTransitionAnimation(this, authorPair, photoPair, statusPair,
              navPair);
    }
    return options;
  }
}
