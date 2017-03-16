package com.sync.tuya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sync.tuya.R;
import com.sync.tuya.utils.AnimUtils;
import com.sync.tuya.utils.Constant;
import java.util.List;

/**
 * Created by YH on 2017-03-16.
 */

public class MenuAdapter extends BaseAdapter {

  private Context mContext;
  private List<String> mList;
  private int width;

  public MenuAdapter(Context context, List<String> list) {
    this.mList = list;
    this.mContext = context;
    this.width = context.getResources().getDisplayMetrics().widthPixels;
  }

  @Override public int getCount() {
    return mList.size();
  }

  @Override public Object getItem(int position) {
    return mList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
    ((ImageView) convertView.findViewById(R.id.menu_item_iv)).setImageResource(Constant.IMG_ID[position]);
    ((TextView) convertView.findViewById(R.id.menu_item_tv)).setText(Constant.MENU_NAME[position]);
    convertView.startAnimation(AnimUtils.getItemAlpha(position));
    convertView.startAnimation(AnimUtils.getItemTran(position, width));
    return convertView;
  }
}
