package com.mari.dagger2example;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

  Context context;

  public UserModule(Context context) {
    this.context = context;
  }

  @Provides
  public ApiService providerApiService() {
    return new ApiService(context);
  }

  @Provides
  public String providerUrl() {
    return "http://www.baidu.com";
  }

  @Provides
  public UserManager providerUserManager(ApiService apiService, UserStore userStore) {
    return new UserManager(userStore, apiService);
  }
}
