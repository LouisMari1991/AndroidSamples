package com.googlesamples.topeka.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.googlesamples.topeka.R;

/**
 * Author：Administrator on 2016/8/20 0020 17:40
 * Contact：289168296@qq.com
 */
public class CategorySelectionFragment extends Fragment{

  public static CategorySelectionFragment newInstance() {
    return new CategorySelectionFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_categories, container, false);
  }
}
