package com.sync.rxjavasamples;

import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author：Administrator on 2017/3/26 0026 22:47
 * Contact：289168296@qq.com
 */
public class BaseActivity extends AppCompatActivity {

  private CompositeSubscription mCompositeSubscription;

  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }
    this.mCompositeSubscription.add(s);
  }

  public void unsubscribe() {
    if (this.mCompositeSubscription != null) {
      this.mCompositeSubscription.unsubscribe();
    }
  }

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);

    ButterKnife.bind(this);

    ActionBar supportActionBar = getSupportActionBar();
    if (null != supportActionBar) supportActionBar.setDisplayHomeAsUpEnabled(true);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void setContentView(View view) {
    super.setContentView(view);

    ButterKnife.bind(this);
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);

    ButterKnife.bind(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    ButterKnife.unbind(this);

    unsubscribe();
  }
}
