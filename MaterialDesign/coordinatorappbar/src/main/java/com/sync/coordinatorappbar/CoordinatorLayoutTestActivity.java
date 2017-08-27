package com.sync.coordinatorappbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author：Mari on 2017-08-25 21:24
 * Contact：289168296@qq.com
 */
public class CoordinatorLayoutTestActivity extends AppCompatActivity {

  TabLayout    mTabLayout;
  ViewPager    mViewPager;
  LinearLayout mLinearLayout;

  AppBarLayout mAppBarLayout;
  View         mStickyView;
  View         mImageHeader;

  private List<Fragment> mFragmentList = new ArrayList<Fragment>() {
    {
      add(MyFragment.newInstance());
      add(MyFragment.newInstance());
      add(MyFragment.newInstance());
    }
  };

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator_layout_test);
    mTabLayout = (TabLayout) findViewById(R.id.table_layout);
    mViewPager = (ViewPager) findViewById(R.id.view_pager);
    mLinearLayout = (LinearLayout) findViewById(R.id.ll);
    mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
    mStickyView = findViewById(R.id.sticky_view);
    mImageHeader = findViewById(R.id.img_header);

    initViewPager();
    setAppBarListener();
  }

  private void setAppBarListener() {
    mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int minScrollHeight = mImageHeader.getMeasuredHeight();// 图片高度
        int margin = minScrollHeight + verticalOffset;
        Log.i("CoordinatorLayoutTestActivity", " verticalOffset : "
            + verticalOffset
            + ", minScrollHeight: + "
            + minScrollHeight
            + " , margin : "
            + margin);
        margin = margin > 0 ? 0 : margin;
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mStickyView.getLayoutParams();
        lp.topMargin = margin;
        mStickyView.setLayoutParams(lp);
      }
    });
  }

  private void initViewPager() {
    mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override public Fragment getItem(int position) {
        return mFragmentList.get(position);
      }

      @Override public int getCount() {
        return mFragmentList.size();
      }

      @Override public CharSequence getPageTitle(int position) {
        return "Tab" + position;
      }
    });
    mTabLayout.setupWithViewPager(mViewPager);
  }
}
