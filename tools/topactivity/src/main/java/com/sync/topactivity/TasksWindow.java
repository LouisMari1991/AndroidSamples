package com.sync.topactivity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Author：Administrator on 2017/2/5 0005 10:19
 * Contact：289168296@qq.com
 */
public class TasksWindow {

  private static WindowManager.LayoutParams sWindowPatams;
  private static WindowManager sWindowManager;
  private static View sView;

  public static void init(final Context context) {
    sWindowManager =
        (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    sWindowPatams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT, 2005, 0x18, PixelFormat.TRANSLUCENT);
    sWindowPatams.gravity = Gravity.LEFT + Gravity.TOP;
    sView = LayoutInflater.from(context).inflate(R.layout.window_tasks, null);
  }

  public static void show(Context context, final String text) {
    if (sWindowManager == null) {
      init(context);
    }
    TextView textView = (TextView) sView.findViewById(R.id.text);
    textView.setText(text);
    try {
      sWindowManager.addView(sView, sWindowPatams);
    } catch (Exception e) {
    }
  }

  public static void dismiss(Context context) {
    try {
      sWindowManager.removeView(sView);
    } catch (Exception e) {
    }
  }
}
