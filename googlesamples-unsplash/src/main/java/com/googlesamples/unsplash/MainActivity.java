package com.googlesamples.unsplash;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.widget.ProgressBar;
import com.googlesamples.unsplash.model.Photo;
import com.googlesamples.unsplash.ui.TransitionCallback;
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
  }
}
