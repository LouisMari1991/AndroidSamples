package com.sync.wearable.flashlight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private ViewPager mViewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mViewPager = (ViewPager) findViewById(R.id.pager);
    final LightFragmentAdapter adapter = new LightFragmentAdapter(getSupportFragmentManager());
    adapter.addFragment(new WhiteLightFragment());
    final PartyLightFragment partyLightFragment = new PartyLightFragment();
    adapter.addFragment(partyLightFragment);
    mViewPager.setAdapter(adapter);
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        if (position == 1) {
          partyLightFragment.startCycling();
        } else {
          partyLightFragment.stopCycling();
        }
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
  }

  static class LightFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;

    public LightFragmentAdapter(FragmentManager fm) {
      super(fm);
      mFragments = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
      mFragments.add(fragment);
      // Update the pager when adding a fragment.
      notifyDataSetChanged();
    }

    @Override public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override public int getCount() {
      return mFragments.size();
    }
  }

  public static class WhiteLightFragment extends Fragment {
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.white_light, container, false);
    }
  }

  public static class PartyLightFragment extends Fragment {
    private PartyLightView mView;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
      mView = (PartyLightView) inflater.inflate(R.layout.party_light, container, false);
      return mView;
    }

    public void startCycling() {
      mView.startCycling();
    }

    public void stopCycling() {
      mView.stopCycling();
    }
  }
}
