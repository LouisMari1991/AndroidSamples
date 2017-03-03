package com.sync.toolbartitleanimation.helper;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

/**
 * Author：Administrator on 2017/3/2 0002 23:09
 * Contact：289168296@qq.com
 */
public class ToolbarColorizeHelper {

  /**
   *
   * @param toolbarView
   * @param toolbarIconsColor
   * @param activity
   */
  public static void colorizeToolbar(Toolbar toolbarView, int toolbarIconsColor, Activity activity) {

    final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(toolbarIconsColor, PorterDuff.Mode.MULTIPLY);

    for (int i = 0; i < toolbarView.getChildCount(); i++) {
      final View v = toolbarView.getChildAt(i);

      if (v instanceof ImageButton) {
        ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
      }

      if (v instanceof ActionMenuView) {
        for (int j = 0; j < ((ActionMenuView) v).getChildCount(); j++) {
          final View innerView = ((ActionMenuView) v).getChildAt(j);
          if (innerView instanceof ActionMenuView) {
            for (int k = 0; k < ((ActionMenuItemView) innerView).getCompoundDrawables().length; k++) {
              if (((ActionMenuItemView) innerView).getCompoundDrawables()[k] != null) {
                final int finalK = k;
                innerView.post(new Runnable() {
                  @Override public void run() {
                    ((ActionMenuItemView) innerView).getCompoundDrawables()[finalK].setColorFilter(colorFilter);
                  }
                });
              }
            }
          }
        }
      }
    }
  }

  private static void setOverflowButtonColor(final Activity activity, final PorterDuffColorFilter colorFilter) {

  }
}
