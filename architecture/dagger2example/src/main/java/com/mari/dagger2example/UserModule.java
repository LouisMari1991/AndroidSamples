package com.mari.dagger2example;

import android.content.Context;
import com.sync.logger.Logger;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

  Context context;

  public UserModule(Context context) {
    this.context = context;
  }

  @Provides
  @Dev
  public ApiService providerApiServiceDev(String url) {
    ApiService apiService = new ApiService(url);
    Logger.i("providerApiServiceDev , ApiService : " + apiService);
    return apiService;
  }

  @Provides
  @Release
  public ApiService providerApiServiceRelease(Context context) {
    ApiService apiService = new ApiService(context);
    Logger.i("providerApiServiceRelease , ApiService : " + apiService);
    return apiService;
  }

  @Provides
  public Context providerContext() {
    return context;
  }

  @Provides
  public String providerUrl() {
    return "http://www.baidu.com";
  }

  @Provides
  public UserManager providerUserManager() {
    return new UserManager();
  }
}
