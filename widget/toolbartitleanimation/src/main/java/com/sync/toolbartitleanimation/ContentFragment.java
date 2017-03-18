package com.sync.toolbartitleanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Author：Administrator on 2017/3/18 0018 14:45
 * Contact：289168296@qq.com
 */
public class ContentFragment extends Fragment {

  private static final String TITLE = "title";

  private String mTitle;

  public static ContentFragment newInstance(String title) {
    ContentFragment fragment = new ContentFragment();
    Bundle b = new Bundle();
    b.putString(TITLE, title);
    fragment.setArguments(b);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mTitle = getArguments().getString(TITLE);
    }
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_content, container, false);
    TextView textTitle = (TextView) root.findViewById(R.id.title_text);
    textTitle.setText(mTitle);
    return root;
  }
}
