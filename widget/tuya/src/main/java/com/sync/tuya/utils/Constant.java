package com.sync.tuya.utils;

import com.sync.tuya.R;

/**
 * Created by YH on 2017-03-16.
 */

public class Constant {

  public static final int[] IMG_ID;
  public static final String[] MENU_NAME;

  static {
    MENU_NAME = new String[] { "画笔颜色", "画笔宽度", "清除画布", "保存绘画" };
    IMG_ID = new int[] { R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4 };
  }
}
