package com.sync.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import www.yihuacomputer.base.databinding.ActivityBaseBinding;
import www.yihuacomputer.base.helper.PerfectClickListener;
import www.yihuacomputer.base.helper.StatusBarHelper;

/**
 * Created by YH on 2017-01-13.
 */

public class BaseActivity<SV extends ViewDataBinding> extends AppCompatActivity {

  protected SV bindingView;

  private ActivityBaseBinding mBaseBinding;
  private AnimationDrawable mAnimationDrawable;

  private LinearLayout llProgressBar;
  private View refresh;

  // RxAndroid
  private CompositeSubscription mCompositeSubscription;

  protected <T extends View> T getView(@IdRes int id) {
    return (T) findViewById(id);
  }

  @Override public void setContentView(@LayoutRes int layoutResID) {
    mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
    bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
    // content
    RelativeLayout.LayoutParams params =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    bindingView.getRoot().setLayoutParams(params);
    RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
    mContainer.addView(bindingView.getRoot());
    getWindow().setContentView(mBaseBinding.getRoot());

    // 设置透明状态栏
    StatusBarHelper.setColor(this, getResources().getColor(R.color.colorTheme), 0);
    llProgressBar = getView(R.id.ll_progress_bar);
    refresh = getView(R.id.ll_error_refresh);
    ImageView img = getView(R.id.img_progress);
    // 加载动画
    mAnimationDrawable = (AnimationDrawable) img.getDrawable();
    // 默认进入页面就开启动画
    if (!mAnimationDrawable.isRunning()) {
      mAnimationDrawable.start();
    }
    // 设置toolbar
    setUpToolBar();
    // 点击加载失败布局
    refresh.setOnClickListener(new PerfectClickListener() {
      @Override protected void onNoDoubleClick(View v) {
        showLoading();
        onRefresh();
      }
    });

    bindingView.getRoot().setVisibility(View.GONE);
  }

  /**
   * 失败后点击刷新
   */
  protected void onRefresh() {

  }

  private void setUpToolBar() {
    setSupportActionBar(mBaseBinding.toolBar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      //去除默认Title显示
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
    }
    mBaseBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
  }

  public void setTitle(CharSequence text) {
    mBaseBinding.toolBarTitle.setText(text);
  }

  public void setTitle(@StringRes int strResId) {
    mBaseBinding.toolBarTitle.setText(strResId);
  }

  protected void showLoading() {
    if (llProgressBar.getVisibility() != View.VISIBLE) {
      llProgressBar.setVisibility(View.VISIBLE);
    }
    // 开始动画
    if (!mAnimationDrawable.isRunning()) {
      mAnimationDrawable.start();
    }
    if (bindingView.getRoot().getVisibility() != View.GONE) {
      bindingView.getRoot().setVisibility(View.GONE);
    }
    if (refresh.getVisibility() != View.GONE) {
      refresh.setVisibility(View.GONE);
    }
  }

  protected void showContentView() {
    if (llProgressBar.getVisibility() != View.GONE) {
      llProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (refresh.getVisibility() != View.GONE) {
      refresh.setVisibility(View.GONE);
    }
    if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
      bindingView.getRoot().setVisibility(View.VISIBLE);
    }
  }

  protected void showError() {
    if (llProgressBar.getVisibility() != View.GONE) {
      llProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (refresh.getVisibility() != View.VISIBLE) {
      refresh.setVisibility(View.VISIBLE);
    }
    if (bindingView.getRoot().getVisibility() != View.GONE) {
      bindingView.getRoot().setVisibility(View.GONE);
    }
  }

  protected void showError(String errText) {
    ((TextView) refresh.findViewById(R.id.text_err)).setText(errText);
    showError();
  }

  protected void showError(@StringRes int strRes) {
    ((TextView) refresh.findViewById(R.id.text_err)).setText(strRes);
    showError();
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      View v = getCurrentFocus();
      if (isShouldHideInput(v, ev)) {
        hideSoftInput(v.getWindowToken());
      }
    }
    return super.dispatchTouchEvent(ev);
  }

  /**
   * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
   */
  private boolean isShouldHideInput(View v, MotionEvent event) {
    if (v != null && (v instanceof EditText)) {
      int[] l = { 0, 0 };
      v.getLocationInWindow(l);
      int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
      if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
        // 点击EditText的事件，忽略它。
        return false;
      } else {
        return true;
      }
    }
    // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
    return false;
  }

  /**
   * 多种隐藏软件盘方法的其中一种
   */
  private void hideSoftInput(IBinder token) {
    if (token != null) {
      InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

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
