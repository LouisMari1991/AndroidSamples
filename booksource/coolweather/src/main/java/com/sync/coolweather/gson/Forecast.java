package com.sync.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YH on 2017-02-17.
 */

public class Forecast {

  public String date;

  @SerializedName("tmp") public Temperature temperature;

  @SerializedName("cond") public More more;

  public class Temperature {
    public String max;
    public String min;
  }

  public class More {
    public String info;
  }
}
