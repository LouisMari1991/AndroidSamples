package com.sync.androidsamples.googlesamples.topeka.topeka.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import com.sync.androidsamples.googlesamples.topeka.topeka.model.Avatar;
import com.sync.androidsamples.googlesamples.topeka.topeka.model.Player;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class SignInFragment extends Fragment {


  private static final String ARG_EDIT = "EDIT";
  private static final String KEY_SELECTED_AVATAR_INDEX = "selectAvatarIndex";
  private Player mPlayer;
  private EditText mFirstName;
  private EditText mLastName;
  private Avatar mSelectAvator;
  private View mSelectedAvatarView;
  private GridView mAvatarGrid;
  private FloatingActionButton mDoneFab;
  private boolean edit;

  public static SignInFragment newInstance(boolean edit) {
    Bundle args = new Bundle();
    args.putBoolean(ARG_EDIT, edit);
    SignInFragment fragment = new SignInFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    final int saveAvatarIndex = savedInstanceState.getInt(KEY_SELECTED_AVATAR_INDEX);
    if (saveAvatarIndex != GridView.INVALID_POSITION) {
      mSelectAvator = Avatar.values()[saveAvatarIndex];
    }
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    return super.onCreateView(inflater, container, savedInstanceState);
  }
}

























