package com.googlesamples.topeka.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlesamples.topeka.R;

/**
 * Author：Administrator on 2016/8/29 0029 21:48
 * Contact：289168296@qq.com
 */
public class CategoryAdapter extends RecyclerView.Adapter {

  public static final String DEAWABLE =
      "build/intermediates/exploded-aar/com.android.support/design/24.1.1/res/drawable";
  private static final String ICON_CATEGORY = "icon_category_";
  private final Resources mResources;
  private final String mPackageName;
  private final LayoutInflater mLayoutInflater;
  private final Activity mActivity;
  //private List<Cate>

  private AdapterView.OnItemClickListener mOnItemClickListener;

  public CategoryAdapter(Activity activity) {
    this.mActivity = activity;
    mResources = mActivity.getResources();
    mPackageName = mActivity.getPackageName();
    mLayoutInflater = LayoutInflater.from(activity.getApplicationContext());

  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }

  /**
   * Convenience method for color loading.
   *
   * @param colorRes The resource id of the color to load.
   * @return The loaded color.
   *
   * 2016/8/30 0030 21:38
   */
  private int getColor(@ColorRes int colorRes) {
    return ContextCompat.getColor(mActivity, colorRes);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    final ImageView icon;
    final TextView title;

    public ViewHolder(View container) {
      super(container);
      icon = (ImageView) container.findViewById(R.id.category_icon);
      title = (TextView) container.findViewById(R.id.category_title);
    }
  }
}
