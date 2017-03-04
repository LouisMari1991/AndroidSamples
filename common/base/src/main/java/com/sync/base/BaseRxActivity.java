package com.sync.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by YH on 2017-02-20.
 */

public class BaseRxActivity extends AppCompatActivity {

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
  }

  // RxAndroid
  private CompositeSubscription mCompositeSubscription;

  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }
    this.mCompositeSubscription.add(s);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
      this.mCompositeSubscription.unsubscribe();
    }
  }

  public void removeSubscription() {
    if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
      this.mCompositeSubscription.unsubscribe();
    }
  }
}
