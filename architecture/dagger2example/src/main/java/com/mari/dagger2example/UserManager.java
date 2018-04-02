package com.mari.dagger2example;

import javax.inject.Inject;

public class UserManager {

  @Inject
  UserStore userStore;

  @Release
  @Inject
  ApiService apiService;

  @Inject
  public UserManager() {
  }

  public void register() {
    apiService.register();
    userStore.login();
  }
}
