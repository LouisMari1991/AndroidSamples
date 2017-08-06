package com.sync.scrollshapeui.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sync.logger.Logger;
import com.sync.scrollshapeui.R;
import com.sync.scrollshapeui.adapter.ListAdapter;
import com.sync.scrollshapeui.databinding.ActivityDetailBinding;
import com.sync.scrollshapeui.utils.CommonUtils;
import com.sync.scrollshapeui.utils.CustomChangeBounds;
import com.sync.scrollshapeui.utils.StatusBarUtil;
import com.sync.scrollshapeui.view.MyNestedScrollView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Description:
 * Author：Mari on 2017-08-05 23:31
 * Contact：289168296@qq.com
 */
public class NeteasePlaylistActivity extends AppCompatActivity {

  public final static String IMAGE_URL_LARGE  = "https://img5.doubanio.com/lpic/s4477716.jpg";
  public final static String IMAGE_URL_SMALL  = "https://img5.doubanio.com/spic/s4477716.jpg";
  public final static String IMAGE_URL_MEDIUM = "https://img5.doubanio.com/mpic/s4477716.jpg";
  public final static String PARAM            = "isRecyclerView";

  ActivityDetailBinding mBinding;
  boolean               isRecyclerView;

  // 高斯背景的高度
  int imageBgHeight;

  int slidingDistance;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
    if (getIntent() != null) {
      isRecyclerView = getIntent().getBooleanExtra(PARAM, true);
    }
    setMotion();
    setTitleBar();
    setPicture();

    initSlideShapeTheme();

    if (isRecyclerView) {
      setAdapter();
    } else {
      setText();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    // 不设置这个将导致RecyclerView占满全屏幕
    mBinding.xrvList.setFocusable(false);
  }

  private void initSlideShapeTheme() {
    // 加载titlebar背景,加载后将背景设为透明
    setImgHeaderBg();

    // Toolbar
    int toolbarHeight = mBinding.titleToolBar.getLayoutParams().height;

    Logger.i(" toolbarHeight : " + toolbarHeight);
    Logger.i(" getDimens : " + (int) (CommonUtils.getDimens(R.dimen.nav_bar_height)));

    // Toolbar+状态栏的高度　
    final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(this);

    // 使背景图向上移动到图片的最低端,保留 toolbar+状态栏的高
    mBinding.ivTitleHeadBg.setVisibility(View.VISIBLE);

    ViewGroup.LayoutParams params = mBinding.ivTitleHeadBg.getLayoutParams();
    ViewGroup.MarginLayoutParams ivTitleHeadBgParams =
        (ViewGroup.MarginLayoutParams) mBinding.ivTitleHeadBg.getLayoutParams();

    // ImageView 高度减去 toolbar高度和状态栏高度总和
    int marginTop = params.height - headerBgHeight;

    Logger.i(" marginTop : " + marginTop);

    // 是的ImageView 的高度为 toolbar高度和状态栏高度总和, 即作为 toolbar和状态栏的背景
    ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

    mBinding.ivTitleHeadBg.setImageAlpha(0);

    // 为头部是View的界面设置状态栏透明
    StatusBarUtil.setTranslucentImageHeader(this, 0, mBinding.titleToolBar);

    ViewGroup.LayoutParams imgItemBgPatams = mBinding.include.imgItemBg.getLayoutParams();

    // 获取高斯背景图的高度
    imageBgHeight = imgItemBgPatams.height;

    Logger.i(" 获取高斯背景图的高度 , imageBgHeight ： " + imageBgHeight);

    // 监听滑动改变透明度
    initScrollViewListener();
  }

  /**
   * 上下滑动，渐变背景图透明度
   */
  private void initScrollViewListener() {
    // 为了兼容api23以下
    mBinding.nsvScrollview.setOnMyScrollChangeListener(new MyNestedScrollView.ScrollInterface() {
      @Override public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        scrollChangeHeader(scrollY);
      }
    });

    int titleBarAndStatusHeight =
        (int) (CommonUtils.getDimens(R.dimen.nav_bar_height) + StatusBarUtil.getStatusBarHeight(this));

    // 268
    // 290dp - (Toolbar+状态栏的高度) - Toolbar高度
    // 1160 - (Toolbar+状态栏的高度) - 224 = 608
    slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) CommonUtils.getDimens(R.dimen.nav_bar_height);

    Logger.i("initScrollViewListener , slidingDistance : " + slidingDistance);
  }

  /**
   * 根据页面滑动距离改变Header透明度方法
   */
  private void scrollChangeHeader(int scrollY) {

    Logger.i(" scrollChangeHeader ,  scrollY : " + scrollY);

    if (scrollY < 0) {
      scrollY = 0;
    }

    float alpha = Math.abs(scrollY) * 1.f / (slidingDistance);
    Drawable drawable = mBinding.ivTitleHeadBg.getDrawable();

    Logger.i(" scrollChangeHeader , alpha : " + alpha);

    if (drawable != null) {
      if (scrollY <= slidingDistance) {
        drawable.mutate().setAlpha((int) (alpha * 255));
        mBinding.ivTitleHeadBg.setImageDrawable(drawable);
      } else {
        drawable.mutate().setAlpha(255);
        mBinding.ivTitleHeadBg.setImageDrawable(drawable);
      }
    }
  }

  /**
   * 加载titlebar背景,加载后将背景设为透明
   * 监听图片显示，在显示之后将其设置为透明色，然后在滑动的时候渐变。
   * 这里值得注意的是在设置图片时不要设置加载中的图片，不然初始化时达不到透明的效果。
   */
  private void setImgHeaderBg() {
    Glide.with(this).load(NeteasePlaylistActivity.IMAGE_URL_MEDIUM)
        //.placeholder(R.drawable.stackblur_default)
        .error(R.drawable.stackblur_default).bitmapTransform(new BlurTransformation(this, 14, 3))// 设置高斯模糊
        .listener(new RequestListener<String, GlideDrawable>() {//监听加载状态
          @Override
          public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
              boolean isFromMemoryCache, boolean isFirstResource) {
            // Toolbar背景设为透明
            mBinding.titleToolBar.setBackgroundColor(Color.TRANSPARENT);
            // 背景图初始化为全透明
            mBinding.ivTitleHeadBg.setImageAlpha(0);
            mBinding.ivTitleHeadBg.setVisibility(View.VISIBLE);
            return false;
          }
        }).into(mBinding.ivTitleHeadBg);
  }

  /**
   * 高斯背景图
   */
  private void setPicture() {
    Glide.with(this)
        .load(IMAGE_URL_LARGE)
        .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width),
            (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
        .into(mBinding.include.ivOnePhoto);

    // "14":模糊度；"3":图片缩放3倍后再进行模糊
    Glide.with(this)
        .load(IMAGE_URL_MEDIUM)
        .error(R.drawable.stackblur_default)
        .placeholder(R.drawable.stackblur_default)
        .crossFade(500)
        .bitmapTransform(new BlurTransformation(this, 14, 3))
        .into(mBinding.include.imgItemBg);
  }

  /**
   * toolbar设置
   */
  private void setTitleBar() {
    setSupportActionBar(mBinding.titleToolBar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      //去除默认Title显示
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
    }
    mBinding.titleToolBar.setTitle("1988：我想和这个世界谈谈");
    mBinding.titleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
  }

  private void setMotion() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //定义ArcMotion
      ArcMotion arcMotion = new ArcMotion();
      arcMotion.setMinimumHorizontalAngle(50f);
      arcMotion.setMinimumVerticalAngle(50f);
      //插值器，控制速度
      Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

      //实例化自定义的ChangeBounds
      CustomChangeBounds changeBounds = new CustomChangeBounds();
      changeBounds.setPathMotion(arcMotion);
      changeBounds.setInterpolator(interpolator);
      changeBounds.addTarget(mBinding.include.ivOnePhoto);
      //将切换动画应用到当前的Activity的进入和返回
      getWindow().setSharedElementEnterTransition(changeBounds);
      getWindow().setSharedElementReturnTransition(changeBounds);
    }
  }

  public static void start(Activity context, ImageView imageView, boolean isRecyclerView) {
    Intent intent = new Intent(context, NeteasePlaylistActivity.class);
    intent.putExtra(PARAM, isRecyclerView);
    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, imageView,
        CommonUtils.getString(R.string.transition_book_img));//与xml文件对应
    ActivityCompat.startActivity(context, intent, options.toBundle());
  }

  /**
   * 显示文本
   */
  private void setText() {
    mBinding.tvTxt.setVisibility(View.VISIBLE);
    mBinding.xrvList.setVisibility(View.GONE);
  }

  /**
   * 设置RecyclerView
   */
  private void setAdapter() {
    mBinding.tvTxt.setVisibility(View.GONE);
    mBinding.xrvList.setVisibility(View.VISIBLE);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mBinding.xrvList.setLayoutManager(mLayoutManager);
    // 需加，不然滑动不流畅
    mBinding.xrvList.setNestedScrollingEnabled(false);
    mBinding.xrvList.setHasFixedSize(false);
    final ListAdapter adapter = new ListAdapter(this);
    adapter.notifyDataSetChanged();
    mBinding.xrvList.setAdapter(adapter);
  }
}
