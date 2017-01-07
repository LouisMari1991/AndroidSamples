package com.googlesamples.unsplash.ui.pager;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.googlesamples.unsplash.R;
import com.googlesamples.unsplash.data.model.Photo;
import com.googlesamples.unsplash.databinding.DetailViewBinding;
import com.googlesamples.unsplash.ui.DetailSharedElementEnterCallback;
import com.googlesamples.unsplash.ui.ImageSize;
import java.util.List;

/**
 * Adapter for paging detail views.
 *
 * Author：Administrator on 2016/9/2 0002 21:20
 * Contact：289168296@qq.com
 */
public class DetailViewPagerAdapter extends PagerAdapter {

  private final List<Photo> allPhotos;
  private final LayoutInflater layoutInflater;
  private final int photoWidth;
  private final Activity host;
  private DetailSharedElementEnterCallback sharedElementCallback;

  public DetailViewPagerAdapter(@NonNull Activity activity, @NonNull List<Photo> photos,
      @NonNull DetailSharedElementEnterCallback callback) {
    layoutInflater = LayoutInflater.from(activity);
    allPhotos = photos;
    photoWidth = activity.getResources().getDisplayMetrics().widthPixels;
    host = activity;
    sharedElementCallback = callback;
  }

  @Override public int getCount() {
    return allPhotos.size();
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    DetailViewBinding binding =
        DataBindingUtil.inflate(layoutInflater, R.layout.detail_view, container, false);
    binding.setData(allPhotos.get(position));
    binding.executePendingBindings();
    container.addView(binding.getRoot());
    return binding;
  }

  private void onViewBound(DetailViewBinding binding) {
    Glide.with(host).load(binding.getData().getPhotoUrl(photoWidth))
        //设置占位图
        .placeholder(R.color.placeholder)
        //Image Resizing
        .override(ImageSize.NORMAL[0], ImageSize.NORMAL[1]).into(binding.photo);
  }

  /**
   * startUpdate、setPrimaryItem、finishUpdate这三个方法在
   * 页面切换时会多次调用；而且当Touch屏幕不滑动时 也会调用；故不建议在这几个方法里面做更新UI；
   */
  @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
    if (object instanceof DetailViewBinding) {
      //sharedElementCallback.
    }
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return object instanceof DetailViewBinding && view.equals(
        ((DetailViewBinding) object).getRoot());
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(((DetailViewBinding) object).getRoot());
  }
}
