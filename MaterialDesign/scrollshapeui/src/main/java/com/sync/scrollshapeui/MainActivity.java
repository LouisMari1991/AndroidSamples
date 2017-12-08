package com.sync.scrollshapeui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.bumptech.glide.Glide;
import com.sync.scrollshapeui.databinding.ActivityMainBinding;
import com.sync.scrollshapeui.ui.NeteasePlaylistActivity;
import com.sync.scrollshapeui.ui.NeteasePlaylistActivity2;
import com.sync.scrollshapeui.utils.CommonUtils;

public class MainActivity extends AppCompatActivity {

  ActivityMainBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    setTitle("仿网易云音乐歌单详情页");

    setTitle("仿网易云音乐歌单详情页");
    Glide.with(this)
        .load(NeteasePlaylistActivity2.IMAGE_URL_LARGE)
        .crossFade(500)
        .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width),
            (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
        .into(binding.ivSongList);

    binding.tvRecyclerview.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        NeteasePlaylistActivity2.start(MainActivity.this, binding.ivSongList, true);
      }
    });

    binding.tvTxtShow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        NeteasePlaylistActivity2.start(MainActivity.this, binding.ivSongList, false);
      }
    });
  }
}
