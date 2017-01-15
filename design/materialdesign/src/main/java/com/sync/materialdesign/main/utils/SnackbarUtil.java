package com.sync.materialdesign.main.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import com.sync.materialdesign.R;

/**
 * Author：Administrator on 2017/1/15 0015 16:19
 * Contact：289168296@qq.com
 */
public class SnackbarUtil {
  private static Snackbar mSnackbar;

  public static void show(View v, String msg, int flag) {
    if (flag == 0) { // 短时显示
      mSnackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
    } else { // 长时显示
      mSnackbar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
    }

    mSnackbar.show();
    // Snackbar中有一个可点击的文字，这里设置点击所触发的操作。
    mSnackbar.setAction(R.string.close, new View.OnClickListener() {
      @Override public void onClick(View v) {
        // Snackbar在点击“关闭”后消失
        mSnackbar.dismiss();
      }
    });
  }
}
