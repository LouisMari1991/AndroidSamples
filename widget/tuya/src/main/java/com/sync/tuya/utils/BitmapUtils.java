package com.sync.tuya.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by YH on 2017-03-16.
 */

public class BitmapUtils {
  public static final boolean saveBitmap(String name, Bitmap bmp) {
    File file = new File(Environment.getExternalStorageDirectory() + "/" + name + ".png");
    try {
      file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
      fos.flush();
      fos.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
