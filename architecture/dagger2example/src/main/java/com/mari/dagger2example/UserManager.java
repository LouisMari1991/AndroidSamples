package com.mari.dagger2example;

public class UserManager {

  UserStore userStore;
  ApiService apiService;

  public UserManager(UserStore userStore, ApiService apiService) {
    this.userStore = userStore;
    this.apiService = apiService;
  }

  public void register() {
    apiService.register();
    userStore.login();
  }
}
