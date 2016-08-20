package com.sync.androidsamples.google.samples.apps.topeka.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.sync.androidsamples.R;
import com.sync.androidsamples.google.samples.apps.topeka.model.Avatar;
import com.sync.androidsamples.google.samples.apps.topeka.widget.AvatarView;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class AvatarAdapter extends BaseAdapter {


  private static final Avatar[] mAvatars = Avatar.values();

  private final LayoutInflater mLayoutInflater;

  public AvatarAdapter(Context context) {
    mLayoutInflater = LayoutInflater.from(context);
  }

  private void setAvatar(AvatarView mIcon, Avatar avatar){
    mIcon.setAvatar(avatar.getDrawableId());
    mIcon.setContentDescription(avatar.getNameForAccessibility());
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

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView =  mLayoutInflater.inflate(R.layout.item_avatar, parent, false);
    }
    setAvatar((AvatarView) convertView,mAvatars[position]);
    return convertView;
  }
}
