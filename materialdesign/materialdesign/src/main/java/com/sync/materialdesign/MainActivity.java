package com.sync.materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.sync.materialdesign.activity.ScrollingActivity;
import com.sync.materialdesign.list.ItemListActivity;
import com.sync.materialdesign.main.MyFragment;
import com.sync.materialdesign.main.adapter.MyViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class MainActivity extends AppCompatActivity {

  private DrawerLayout mDrawerLayout;
  private Toolbar mToolbar;
  private TabLayout mTabLayout;
  private ViewPager mViewPager;
  private NavigationView mNavigationView;

  private String[] mTitles;
  private List<Fragment> mFragments;
  private MyViewPagerAdapter mViewPagerAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    initData();
    configView();
  }

  private void configView() {
    // 设置显示Toolbar
    setSupportActionBar(mToolbar);
    // 设置DrawerLayout开关指示器，即Toolbar最左边的那个icon
    ActionBarDrawerToggle mActionBarDrawerToggle =
        new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
    mActionBarDrawerToggle.syncState();
    //给NavigationView填充顶部区域，也可在xml中使用app:headerLayout="@layout/header_nav"来设置
    mNavigationView.inflateHeaderView(R.layout.nav_header_main);
    //给NavigationView填充Menu菜单，也可在xml中使用app:menu="@menu/menu_nav"来设置
    mNavigationView.inflateMenu(R.menu.activity_main_drawer);
    onNavigationViewMenuItemSelected();
    // 初始化ViewPager的适配器，并设置给它
    mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
    mViewPager.setAdapter(mViewPagerAdapter);
    // 设置ViewPager最大缓存的页面个数
    mViewPager.setOffscreenPageLimit(5);
    // 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
    onViewPagerChange();
    mTabLayout.setTabMode(MODE_SCROLLABLE);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void onViewPagerChange() {
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        mToolbar.setTitle(mTitles[position]);
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
  }

  private void initData() {
    mTitles = getResources().getStringArray(R.array.tab_titles);
    mFragments = new ArrayList<>();
    for (int i = 0; i < mTitles.length; i++) {
      Bundle b = new Bundle();
      b.putInt("flag", i);
      mFragments.add(i, MyFragment.newInstance(b));
    }
  }

  private void initView() {
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mToolbar = (Toolbar) findViewById(R.id.tool_bar);
    mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
    mViewPager = (ViewPager) findViewById(R.id.view_pager);
    mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
  }

  private void onNavigationViewMenuItemSelected() {
    mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
          case R.id.nav_gallery: {
            Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
            startActivity(intent);
            break;
          }
          case R.id.nav_slideshow: {
            new Handler().postDelayed(new Runnable() {
              @Override public void run() {
                startActivity(new Intent(MainActivity.this, ItemListActivity.class));
              }
            }, 100);
            break;
          }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);// “right” -- end  "left" -- start
        return true;
      }
    });
  }
}
