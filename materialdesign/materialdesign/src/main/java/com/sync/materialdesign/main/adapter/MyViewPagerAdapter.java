package com.sync.materialdesign.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

/**
 * Author：Administrator on 2017/1/15 0015 15:02
 * Contact：289168296@qq.com
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

  private String[] mTitles;
  private List<Fragment> mFragments;

  public MyViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
    super(fm);
    this.mTitles = titles;
    this.mFragments = fragments;
  }

  @Override public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override public int getCount() {
    return mFragments.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    return mTitles[position];
  }
}
