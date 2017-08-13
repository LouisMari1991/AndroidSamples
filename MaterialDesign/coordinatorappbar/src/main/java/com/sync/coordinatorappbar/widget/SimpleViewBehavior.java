package com.sync.coordinatorappbar.widget;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * Description:
 * Author：Mari on 2017-08-13 20:40
 * Contact：289168296@qq.com
 */
public class SimpleViewBehavior extends CoordinatorLayout.Behavior<View> {

  private static final int   UNSPECIFIED_INT   = Integer.MAX_VALUE;
  private static final float UNSPECIFIED_FLOAT = Float.MAX_VALUE;

  private static final int DEPEND_TYPE_HEIGHT = 0;
  private static final int DEPEND_TYPE_WIDTH  = 1;
  private static final int DEPEND_TYPE_X      = 2;
  private static final int DEPEND_TYPE_Y      = 3;
}


