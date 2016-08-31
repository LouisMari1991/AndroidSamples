package com.googlesamples.displayingbitmaps.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import com.googlesamples.displayingbitmaps.BuildConfig;
import com.googlesamples.displayingbitmaps.R;
import com.googlesamples.displayingbitmaps.util.Utils;

/**
 * Created by sync on 2016/5/15.
 */
public class ImageDetailActivity extends FragmentActivity implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        if (BuildConfig.DEBUG) {
            Utils.enableStrictMode();
        }
        super.onCreate(savedInstanceState,
            persistentState);
        setContentView(R.layout.activity_main);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager()
            .getDefaultDisplay().getMetrics(displayMetrics);
        final
        int height = displayMetrics.heightPixels;
        final int width = displayMetrics
            .widthPixels;

//    final int longest = ()
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private
        final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.mSize = size;
        }


        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return null;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return mSize;
        }
    }


    @Override
    public void onClick(View v) {

    }
}
