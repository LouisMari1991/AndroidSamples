package com.sync.scrollshapeui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sync.scrollshapeui.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding  binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    setTitle("仿网易云音乐歌单详情页");

    setTitle("仿网易云音乐歌单详情页");
    //Glide.with(this)
    //    .load(NeteasePlaylistActivity.IMAGE_URL_LARGE)
    //    .crossFade(500)
    //    .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width),
    //        (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
    //    .into(binding.ivSongList);

  }
}
