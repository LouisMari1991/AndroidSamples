package com.googlesamples.displayingbitmaps.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import com.googlesamples.displayingbitmaps.ui.ImageDetailActivity;
import com.googlesamples.displayingbitmaps.ui.ImageGridActivity;

/**
 * Created by sync on 2016/5/21.
 */
public class Utils {

  private Utils() {
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public static void enableStrictMode() {
    StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
            new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog();
    StrictMode.VmPolicy.Builder vmPolicyBuilder =
            new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog();
    if (Utils.hasHoneycomb()) {
      threadPolicyBuilder.penaltyFlashScreen();
      vmPolicyBuilder.setClassInstanceLimit(ImageDetailActivity.class, 1)
              .setClassInstanceLimit(ImageGridActivity.class, 1);
    }
    StrictMode.setThreadPolicy(threadPolicyBuilder.build());
    StrictMode.setVmPolicy(vmPolicyBuilder.build());
  }


  public static boolean hasFroyo() {
    //Can use static final constants like FROYO declared in later versions
    //if the OS since they are inlined at compile time. This is guaranteed behavior.
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
  }

  public static boolean hasGingerbread() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
  }

  public static boolean hasHoneycomb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  public static boolean hasHoneycombMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }

  public static boolean hasJellyBean() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }

  public static boolean hasKitKat() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }


}
