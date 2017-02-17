package com.sync.coolweather.gson;

/**
 * Created by YH on 2017-02-17.
 */

public class AQI {

  public AQICity city;

  public class AQICity {
    public String aqi;
    public String pm25;
  }
}
