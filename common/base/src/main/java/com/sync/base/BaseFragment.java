package com.sync.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by YH on 2017-01-19.
 */

public class BaseFragment<SV extends ViewDataBinding> extends Fragment {

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  protected <T extends View> T getView(@IdRes int id) {
    return (T) getView().findViewById(id);
  }
}
