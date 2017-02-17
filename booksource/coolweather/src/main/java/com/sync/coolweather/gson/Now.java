package com.sync.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YH on 2017-02-17.
 */

public class Now {

  @SerializedName("tmp") public String temperature;

  @SerializedName("cond") public More more;

  public class More {
    @SerializedName("txt") public String info;
  }
}
