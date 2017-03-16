package com.sync.tuya;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.sync.tuya.adapter.MenuAdapter;
import com.sync.tuya.utils.BitmapUtils;
import com.sync.tuya.utils.Constant;
import com.sync.tuya.view.DrawingView;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private Dialog mDialog;

  private DrawingView mDrawingView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //if (Build.VERSION.SDK_INT >= 21) {
    //  View decorView = getWindow().getDecorView();
    //  int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    //      | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    //      | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    //  decorView.setSystemUiVisibility(option);
    //  getWindow().setNavigationBarColor(Color.TRANSPARENT);
    //  getWindow().setStatusBarColor(Color.TRANSPARENT);
    //}
    findViewById(R.id.main_iv_menu).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showDialog();
      }
    });
    mDrawingView = (DrawingView) findViewById(R.id.drawing_view);
  }

  private void showDialog() {
    if (mDialog == null) {
      mDialog = new Dialog(this, R.style.dialog);
      mDialog.setContentView(R.layout.dialog_layout);
      ListView lv = (ListView) mDialog.findViewById(R.id.menu_lv);
      lv.setDividerHeight(0);
      List<String> list = Arrays.asList(Constant.MENU_NAME);
      lv.setAdapter(new MenuAdapter(this, list));
      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          switch (position) {
            case 0:
              mDrawingView.setPaintColor(Color.GREEN);
              break;
            case 1:
              mDrawingView.setPaintWidth(10);
              break;
            case 2:
              mDrawingView.clearCanvas();
              break;
            case 3:
              new Thread() {
                @Override public void run() {
                  final boolean b =
                      BitmapUtils.saveBitmap(String.valueOf(System.currentTimeMillis()), mDrawingView.getBitmap());
                  runOnUiThread(new Runnable() {
                    @Override public void run() {
                      if (b) {
                        Toast.makeText(MainActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                      } else {
                        Toast.makeText(MainActivity.this, "保存失败!", Toast.LENGTH_SHORT).show();
                      }
                    }
                  });
                }
              }.start();
              break;
          }
          mDialog.cancel();
        }
      });
      mDialog.findViewById(R.id.menu_iv).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mDialog.cancel();
        }
      });
    }
    mDialog.show();
  }
}
