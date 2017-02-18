package com.sync.coolweather;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Author：Administrator on 2017/2/16 0016 23:49
 * Contact：289168296@qq.com
 */
public class WeatherActivity extends AppCompatActivity {

  public DrawerLayout drawerLayout;
  public SwipeRefreshLayout swiRefresh;
  public ScrollView weatherLayout;

  private Button navButton;
  private TextView titleCity;
  private TextView titleUpdatesTime;
  private TextView degreeText;
  private TextView weatherInfoText;
  private LinearLayout forecastLayout;
  private TextView apiText;
  private TextView pm25Text;
  private TextView comfortText;
  private TextView carWashText;
  private TextView sportText;
  private ImageView bingPicImg;
  private String mWeatherId;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= 21) {
      View decorView = getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
    setContentView(R.layout.activity_weather);
  }
}
