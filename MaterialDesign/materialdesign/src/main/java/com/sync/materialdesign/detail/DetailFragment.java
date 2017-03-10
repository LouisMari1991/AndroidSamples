package com.sync.materialdesign.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sync.materialdesign.R;

/**
 * Created by YH on 2017-02-15.
 */

public class DetailFragment extends Fragment {

  public static DetailFragment newInstance(String info) {
    DetailFragment fragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putString("info", info);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_detail, null);
    TextView tvInfo = (TextView) rootView.findViewById(R.id.tvInfo);
    tvInfo.setText(getArguments().getString("info"));
    return rootView;
  }
}


