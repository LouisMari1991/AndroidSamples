package com.googlesamples.displayingbitmaps.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sync on 2016/6/4 17:49.
 * E-Mail: Marishunxiang@gmail.com
 */
public class ImageDetailFragment extends Fragment {

  private static final String IMAGE_DATA_EXTRA = "extra_image_data";
  private String mImageUrl;
  private ImageView mImageView;
//  private

  /**
   * Factory method generate a new instance of the fragment given an image number.
   * @param imageUrl The image url to load
   * @return A New instance of ImageDetailFragment with imageNum extras
   */
  public static ImageDetailFragment newInstance(String imageUrl){
    final ImageDetailFragment f = new ImageDetailFragment();

    final Bundle args = new Bundle();
    args.putString(IMAGE_DATA_EXTRA, imageUrl);
    f.setArguments(args);
    return f;
  }

  public ImageDetailFragment() {}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    

    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
