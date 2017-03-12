package com.sync.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.sync.coolweather.gson.Weather;
import com.sync.coolweather.util.HttpUtil;
import com.sync.coolweather.util.Utility;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Author：Administrator on 2017/2/16 0016 23:49
 * Contact：289168296@qq.com
 */
public class WeatherActivity extends AppCompatActivity {

  public DrawerLayout drawerLayout;
  public SwipeRefreshLayout swipeRefresh;
  public ScrollView weatherLayout;

  private Button navButton;
  private TextView titleCity;
  private TextView titleUpdateTime;
  private TextView degreeText;
  private TextView weatherInfoText;
  private LinearLayout forecastLayout;
  private TextView aqiText;
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

    bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
    weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
    titleCity = (TextView) findViewById(R.id.title_city);
    titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
    degreeText = (TextView) findViewById(R.id.degree_text);
    weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
    forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
    aqiText = (TextView) findViewById(R.id.aqi_text);
    pm25Text = (TextView) findViewById(R.id.pm25_text);
    carWashText = (TextView) findViewById(R.id.car_wash_text);
    sportText = (TextView) findViewById(R.id.sport_text);
    swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    navButton = (Button) findViewById(R.id.nav_button);

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    String weatherString = prefs.getString("weather", null);
    if (weatherString != null) {
      Weather weather = Utility.handleWeatherResponse(weatherString);
      mWeatherId = weather.basic.weatherId;
    } else {
      mWeatherId = getIntent().getStringExtra("weather_id");
      weatherLayout.setVisibility(View.INVISIBLE);
    }

    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {

      }
    });
    navButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        drawerLayout.openDrawer(GravityCompat.START);
      }
    });
    String bingPic = prefs.getString("bing_pic", null);
    if (bingPic != null) {
      Glide.with(this).load(bingPic).into(bingPicImg);
    } else {

    }
  }

  public void requestWeather(final String weatherId) {
    String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
    HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        final String responseText = response.body().string();
        final Weather weather = Utility.handleWeatherResponse(responseText);
        runOnUiThread(new Runnable() {
          @Override public void run() {
            if (weather != null && "ok".equals(weather.status)) {
              SharedPreferences.Editor editor =
                  PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
              editor.putString("weather", responseText);
              editor.apply();
              mWeatherId = weather.basic.weatherId;
            } else {
              Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
            }
            swipeRefresh.setRefreshing(false);
          }
        });
      }
    });
    loadBingPic();
  }

  private void loadBingPic() {
    String requestBingPic = "http://guolin.tech/api/bing_pic";

  }

  private void showWeatherInfo(Weather weather) {
    String cityName = weather.basic.cityName;
    String updateTime = weather.basic.update.updateTime.split(" ")[1];
    String degree = weather.now.temperature + "℃";
    String weatherInfo = weather.now.more.info;
    titleCity.setText(cityName);
    
  }

}
