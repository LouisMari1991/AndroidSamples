package com.sync.viewbase;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.sync.viewbase.databinding.ActivityTestBinding;

/**
 * Created by YH on 2017-02-03.
 */

public class TestActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

  private ActivityTestBinding mBinding;

  private static final int MESSAGE_SCROLL_TO = 1;
  private static final int FRAME_COUNT = 30;
  private static final int DELATED = 33;

  private Button mButton;
  private View mView;

  private int mCount;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
  }

  private Handler mHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      switch (msg.what) {
        case MESSAGE_SCROLL_TO: {

        }
      }
    }
  };

  @Override public void onClick(View v) {
    if (v == mButton) {

    }
  }

  @Override public boolean onLongClick(View v) {
    Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show();
    return true;
  }
}
