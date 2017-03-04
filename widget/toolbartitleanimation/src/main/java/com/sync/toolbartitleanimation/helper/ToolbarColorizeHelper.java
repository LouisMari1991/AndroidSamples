package com.sync.toolbartitleanimation.helper;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import com.sync.toolbartitleanimation.R;
import java.util.ArrayList;

//import android.support.v7.internal.view.menu.ActionMenuItemView;
//import android.support.v7.internal.widget.TintImageView;

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

      toolbarView.setTitleTextColor(toolbarIconsColor);
      toolbarView.setSubtitleTextColor(toolbarIconsColor);
      setOverflowButtonColor(activity, colorFilter);
    }
  }

  private static void setOverflowButtonColor(final Activity activity, final PorterDuffColorFilter colorFilter) {
    final String overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description);
    final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
    final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        final ArrayList<View> outView = new ArrayList<>();
        decorView.findViewsWithText(outView, overflowDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        if (outView.isEmpty()) {
          return;
        }
        //TintImageView overflow = (TintImageView) outView.get(0);
        //overflow.setColorFilter(colorFilter);
        removeOnGlobalLayoutListener(decorView, this);
      }
    });
  }

  private static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
      v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
    } else {
      v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
  }
}
