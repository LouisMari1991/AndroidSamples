package com.sync.customviewstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.sync.customviewstudy.R;
import com.sync.customviewstudy.viewgroup.SmileView;

/**
 * Description:
 * Author：Mari on 2017-08-20 20:51
 * Contact：289168296@qq.com
 */
public class SmileActivity extends AppCompatActivity {

  SeekBar mSeekBar;
  ImageView mSmileForce;
  SmileView mSmileView;


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_smile_view);

    mSeekBar = (SeekBar) findViewById(R.id.seekBar);
    mSmileForce = (ImageView) findViewById(R.id.smile_force);

    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mSmileForce.getLayoutParams();
        lp.bottomMargin = progress * 3;
        mSmileForce.setLayoutParams(lp);
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
    mSmileView = (SmileView) findViewById(R.id.smile_view);
    mSmileView.setNum(40,30);
  }
}
