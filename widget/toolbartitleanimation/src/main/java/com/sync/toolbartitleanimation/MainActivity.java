package com.sync.toolbartitleanimation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.sync.toolbartitleanimation.ui.CircleIndicator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  // This represents the toolbars title
  private TextSwitcher mSwitcher;

  private ViewPager mViewPager;
  private CircleIndicator defaultIndicator;
  private Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);

    final ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
    mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
      @Override public View makeView() {
        TextView myText = new TextView(MainActivity.this);
        myText.setGravity(Gravity.START);
        myText.setTextAppearance(MainActivity.this, R.style.TitleTextApperance);
        return myText;
      }
    });

    final Animation IN_SWIPE_BACKWARD = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
    final Animation OUT_SWIPE_BACKWARD = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);

    final Animation IN_SWIPE_FORWARD = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
    final Animation OUT_SWIPE_FORWARD = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);

    mViewPager = (ViewPager) findViewById(R.id.viewpager);
    defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);

    //Retrieve last selected page from preferences of return 0 by default
    final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    final int currentItem = sharedPrefs.getInt("position", 0);

    if (mViewPager != null) {
      final Adapter adapter = setupViewPager(mViewPager);

      // Set pager to last selected page and toolbar title to the corresponding title
      mViewPager.setCurrentItem(currentItem);
      defaultIndicator.setViewPager(mViewPager);
      mSwitcher.setText(adapter.getPageTitle(currentItem));

      mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override public void onPageSelected(int position) {
          // Retrieve previous pager position to determine swipe direction
          int prevPosition = sharedPrefs.getInt("position", 0);

          // Set TextSwitcher animation based on swipe direction
          if (position >= prevPosition) {

          } else {

          }
          mSwitcher.setText(adapter.getPageTitle(position));

          // Store current position in SharedPreferences
          sharedPrefs.edit().putInt("position", position).apply();
        }

        @Override public void onPageScrollStateChanged(int state) {

        }
      });
    }

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "I love this!", Snackbar.LENGTH_LONG).setAction("Dismiss", null).show();
      }
    });
  }

  private Adapter setupViewPager(ViewPager viewPager) {
    Adapter adapter = new Adapter(getSupportFragmentManager());
    adapter.addFragment(ContentFragment.newInstance("Football Content"), "Football");
    adapter.addFragment(ContentFragment.newInstance("Cricket Content"), "Cricket");
    adapter.addFragment(ContentFragment.newInstance("Hockey Content"), "Hockey");
    adapter.addFragment(ContentFragment.newInstance("Tennis Content"), "Tennis");
    adapter.addFragment(ContentFragment.newInstance("Volleyball Content"), "Volleyball");
    adapter.addFragment(ContentFragment.newInstance("Baseball Content"), "Baseball");
    viewPager.setAdapter(adapter);
    return adapter;
  }

  static class Adapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public Adapter(FragmentManager fm) {
      super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
      mFragments.add(fragment);
      mFragmentTitles.add(title);
    }

    @Override public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override public int getCount() {
      return mFragments.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return mFragmentTitles.get(position);
    }
  }
}
