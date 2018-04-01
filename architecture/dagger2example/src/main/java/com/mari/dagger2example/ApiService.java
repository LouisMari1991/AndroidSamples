package com.mari.dagger2example;

import android.content.Context;
import com.sync.logger.Logger;

public class ApiService {

  private Context context;
  private String url;

  public ApiService(Context context) {
    this.context = context;
    Logger.i("ApiService  Constructor , context : " + context);
  }

  public ApiService(String url) {
    this.url = url;
    Logger.i("ApiService  Constructor , url : " + url);
  }

  public void register() {
    Logger.i("Api service -> register()");
  }
}
