package com.sync.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import www.yihuacomputer.base.helper.PerfectClickListener;

/**
 * Created by YH on 2017-01-13.
 */

public abstract class BaseFragment<SV extends ViewDataBinding> extends Fragment {

  protected SV bindingView;
  // fragment是否显示了
  protected boolean mIsVisible = false;
  // 加载中
  private LinearLayout mLlProgressBar;
  // 加载失败
  private LinearLayout mRefresh;
  // 内容布局
  protected RelativeLayout mContainer;
  // 动画
  private AnimationDrawable mAnimationDrawable;
  // RxAndroid
  private CompositeSubscription mCompositeSubscription;

  protected InputMethodManager inputMethodManager;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View ll = inflater.inflate(R.layout.fragment_base, null);
    bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);
    RelativeLayout.LayoutParams params =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    bindingView.getRoot().setLayoutParams(params);
    mContainer = (RelativeLayout) ll.findViewById(R.id.container);
    mContainer.addView(bindingView.getRoot());
    return ll;
  }

  /**
   * 在这里实现Fragment数据的缓加载.
   */
  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      mIsVisible = true;
      onVisible();
    } else {
      mIsVisible = false;
      onInvisible();
    }
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    mLlProgressBar = getView(R.id.ll_progress_bar);
    ImageView img = getView(R.id.img_progress);

    // 加载动画
    mAnimationDrawable = (AnimationDrawable) img.getDrawable();
    // 默认进入页面就开启动画
    if (!mAnimationDrawable.isRunning()) {
      mAnimationDrawable.start();
    }
    mRefresh = getView(R.id.ll_error_refresh);
    // 点击加载失败布局
    mRefresh.setOnClickListener(new PerfectClickListener() {
      @Override protected void onNoDoubleClick(View v) {
        showLoading();
        onRefresh();
      }
    });
    bindingView.getRoot().setVisibility(View.GONE);
  }

  protected void onInvisible() {
  }

  protected void onVisible() {
    loadData();
  }

  /**
   * 显示时加载数据,需要这样的使用
   * 注意声明 isPrepared，先初始化
   * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
   * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
   */
  protected void loadData() {
  }

  /**
   * 布局
   */
  protected abstract int setContent();

  /**
   * 加载失败后点击后的操作
   */
  protected void onRefresh() {

  }

  protected <T extends View> T getView(@IdRes int id) {
    return (T) getView().findViewById(id);
  }

  /**
   * 显示加载中状态
   */
  protected void showLoading() {
    if (mLlProgressBar.getVisibility() != View.VISIBLE) {
      mLlProgressBar.setVisibility(View.VISIBLE);
    }
    // 开始动画
    if (!mAnimationDrawable.isRunning()) {
      mAnimationDrawable.start();
    }
    if (bindingView.getRoot().getVisibility() != View.GONE) {
      bindingView.getRoot().setVisibility(View.GONE);
    }
    if (mRefresh.getVisibility() != View.GONE) {
      mRefresh.setVisibility(View.GONE);
    }
  }

  /**
   * 加载完成的状态
   */
  protected void showContentView() {
    if (mLlProgressBar.getVisibility() != View.GONE) {
      mLlProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (mRefresh.getVisibility() != View.GONE) {
      mRefresh.setVisibility(View.GONE);
    }
    if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
      bindingView.getRoot().setVisibility(View.VISIBLE);
    }
  }

  /**
   * 加载失败点击重新加载的状态
   */
  protected void showError() {
    if (mLlProgressBar.getVisibility() != View.GONE) {
      mLlProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (bindingView.getRoot().getVisibility() != View.GONE) {
      bindingView.getRoot().setVisibility(View.GONE);
    }
    if (mRefresh.getVisibility() != View.VISIBLE) {
      mRefresh.setVisibility(View.VISIBLE);
    }
  }

  protected void showError(String errText) {
    ((TextView) mRefresh.findViewById(R.id.text_err)).setText(errText);
    showError();
  }

  protected void showError(@StringRes int strRes) {
    ((TextView) mRefresh.findViewById(R.id.text_err)).setText(strRes);
    showError();
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

  protected void hideSoftKeyboard() {
    if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
      if (getActivity().getCurrentFocus() != null) {
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
  }
}
