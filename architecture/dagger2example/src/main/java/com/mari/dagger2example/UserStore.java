package com.mari.dagger2example;

import com.sync.logger.Logger;
import javax.inject.Inject;

public class UserStore {

  private String url;

  @Inject
  public UserStore(String url) {
    this.url = url;
    Logger.i("url : " + url);
  }

  public void login() {
    Logger.i("UserStore -> login()");
  }
}
