package com.sync.androidsamples.googlesamples.topeka.topeka.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.sync.androidsamples.googlesamples.topeka.topeka.model.Avatar;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class AvatarAdapter extends BaseAdapter {


  private static final Avatar[] mAvatars = Avatar.values();

  private final LayoutInflater mLayoutInflater;

  public AvatarAdapter(Context context) {
    mLayoutInflater = LayoutInflater.from(context);
  }

  private void setAvatar(){

  }

  @Override public int getCount() {
    return mAvatars.length;
  }

  @Override public Object getItem(int position) {
    return mAvatars[position];
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int i, View convertView, ViewGroup parent) {
    if (null == convertView) {

    }
    return convertView;
  }
}
