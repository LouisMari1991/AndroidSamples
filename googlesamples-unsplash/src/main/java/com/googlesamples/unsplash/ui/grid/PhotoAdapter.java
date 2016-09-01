package com.googlesamples.unsplash.ui.grid;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.googlesamples.unsplash.R;
import com.googlesamples.unsplash.databinding.PhotoItemBinding;
import com.googlesamples.unsplash.model.Photo;
import com.googlesamples.unsplash.ui.ImageSize;
import java.util.ArrayList;

/**
 * Author：Administrator on 2016/9/1 0001 21:47
 * Contact：289168296@qq.com
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder>{

  private final ArrayList<Photo> photos;
  private final int requestPhotoWidth;
  private final LayoutInflater layoutInflater;

  public PhotoAdapter(@NonNull Context context, @NonNull ArrayList<Photo> photos) {
    this.photos = photos;
    requestPhotoWidth = context.getResources().getDisplayMetrics().widthPixels;
    layoutInflater = LayoutInflater.from(context);
  }

  @Override public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    return new PhotoViewHolder(
        (PhotoItemBinding) DataBindingUtil.inflate(layoutInflater, R.layout.photo_item, parent, false));
  }

  @Override public void onBindViewHolder(PhotoViewHolder holder, int position) {
    PhotoItemBinding binding = holder.getBinding();
    Photo data = photos.get(position);
    binding.setData(data);
    //binding.executePendingBindings();
    Glide.with(layoutInflater.getContext())
        .load(holder.getBinding().getData().getPhotoUrl(requestPhotoWidth))
        .placeholder(R.color.placeholder)
        .override(ImageSize.NORMAL[0], ImageSize.NORMAL[1])
        .into(holder.getBinding().photo);

  }

  @Override public int getItemCount() {
    return photos.size();
  }

  @Override public long getItemId(int position) {
    return  photos.get(position).id;
  }
}
