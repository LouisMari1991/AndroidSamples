package com.sync.customviewstudy.viewgroup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sync.customviewstudy.R;
import com.sync.logger.Logger;

/**
 * Description:
 * Author：Mari on 2017-08-20 21:45
 * Contact：289168296@qq.com
 */
public class SmileView extends LinearLayout implements Animator.AnimatorListener {

  public SmileView(Context context) {
    this(context, null);
  }

  public SmileView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SmileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
    bindListener();
  }

  public void setNum(int like, int dislike) {
    //设置百分比
    count = like + dislike;
    fLike = like / count;
    fDis = dislike / count;
    this.like = (int) (fLike * 100);
    this.disLike = 100 - this.like;
    setLike(this.like);
    setDisLike(this.disLike);
  }

  public void setLike(int like) {
    likeNum.setText(like + "%");
  }

  public void setDisLike(int disLike) {
    disNum.setText(disLike + "%");
  }

  float count;

  int dividerMargin = 20; // 分割线间距

  String defaultLick = "喜欢";
  String defaultDis  = "无感";

  int defaultBottom    = 70;
  int defaultTextColor = Color.WHITE;
  int defaultGravity   = Gravity.CENTER_HORIZONTAL;
  int defaultSize      = dip2px(getContext(), 25);

  String defaultShadow = "#7F484848"; // 执行动画时的半透明背景

  int like    = 10; // 点赞数
  int disLike = 20; // 差评数

  float fLike, fDis;

  ImageView imageLike;
  ImageView imageDis;

  AnimationDrawable animLike; // 笑脸帧动画
  AnimationDrawable animDis; // 哭脸帧动画

  ValueAnimator animatorBack; // 背景拉伸动画

  TextView likeNum, disNum, likeText, disText;
  LinearLayout likeBack, disBack, likeAll, disAll;

  int     type    = 0;// 选择执行帧动画的笑脸, 0 : 笑脸, 1 : 哭脸
  boolean isClose = false; //判断收起动画

  private void init() {
    this.removeAllViews();
    // 初始化总布局
    setOrientation(HORIZONTAL);
    setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    setBackgroundColor(Color.TRANSPARENT); // 开始透明

    // 设置百分比
    count = like + disLike;
    fLike = like / count;
    fDis = disLike / count;
    like = (int) (fLike * 100);
    disLike = (int) (fDis * 100);

    // 初始化图片
    imageLike = new ImageView(getContext());
    // 添加动画资源, 或者帧动画
    imageLike.setBackgroundResource(R.drawable.animation_like);
    animLike = (AnimationDrawable) imageLike.getBackground();

    imageDis = new ImageView(getContext());
    imageDis.setBackgroundResource(R.drawable.animation_dislike);
    animDis = (AnimationDrawable) imageDis.getBackground();

    // 初始化文字
    // 喜欢TextView
    likeNum = new TextView(getContext());
    likeNum.setText(like + "%");
    likeNum.setTextColor(defaultTextColor);
    TextPaint likeNumPaint = likeNum.getPaint();
    likeNumPaint.setFakeBoldText(true); // 文字加粗
    likeNum.setTextSize(20f);

    likeText = new TextView(getContext());
    likeText.setText(defaultLick);
    likeText.setTextColor(defaultTextColor);

    // 不喜欢TextView
    disNum = new TextView(getContext());
    disNum.setText(disLike + "%");
    disNum.setTextColor(defaultTextColor);
    TextPaint disNumPaint = disNum.getPaint();
    disNumPaint.setFakeBoldText(true); // 文字加粗
    disNum.setTextSize(20f);

    disText = new TextView(getContext());
    disText.setText(defaultDis);
    disText.setTextColor(defaultTextColor);

    // 初始化布局
    likeBack = new LinearLayout(getContext());
    disBack = new LinearLayout(getContext());
    LayoutParams lp = new LayoutParams(defaultSize, defaultSize);
    likeBack.addView(imageLike, lp);
    disBack.addView(imageDis, lp);
    likeBack.setBackgroundResource(R.drawable.while_background);
    disBack.setBackgroundResource(R.drawable.while_background);

    // 单列总布局
    likeAll = new LinearLayout(getContext());
    disAll = new LinearLayout(getContext());
    likeAll.setOrientation(VERTICAL);
    disAll.setOrientation(VERTICAL);
    likeAll.setGravity(Gravity.CENTER_HORIZONTAL);
    disAll.setGravity(Gravity.CENTER_HORIZONTAL);
    likeAll.setBackgroundColor(Color.TRANSPARENT);
    disAll.setBackgroundColor(Color.TRANSPARENT);

    // 添加文字图片放进一列
    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params.setMargins(0, 10, 0, 0);
    params.gravity = Gravity.CENTER;
    disAll.setGravity(Gravity.CENTER_HORIZONTAL);
    likeAll.setGravity(Gravity.CENTER_HORIZONTAL);

    // 添加子View
    disAll.addView(disNum, params);
    disAll.addView(disText, params);
    disAll.addView(disBack, params);

    likeAll.addView(likeNum, params);
    likeAll.addView(likeText, params);
    likeAll.addView(likeBack, params);

    // 中间分隔线
    ImageView imageView = new ImageView(getContext());
    imageView.setBackground(new ColorDrawable(Color.GRAY));
    LayoutParams imgLp = new LayoutParams(3, 80);
    imgLp.setMargins(dividerMargin, 10, dividerMargin, defaultBottom + 20);
    imgLp.gravity = Gravity.BOTTOM;

    // 将所有View加入layout
    LayoutParams allParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    allParams.setMargins(30, 20, 30, defaultBottom);
    allParams.gravity = Gravity.BOTTOM;
    addView(disAll, allParams);
    addView(imageView, allParams);
    addView(likeAll, allParams);

    // 隐藏文字
    setVisibities(GONE);
  }

  private void setVisibities(int state) {
    likeNum.setVisibility(state);
    likeText.setVisibility(state);
    disNum.setVisibility(state);
    disText.setVisibility(state);
  }

  // 绑定监听
  private void bindListener() {
    imageDis.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        type = 1; // 设置动画对象
        animBack();// 拉伸背景
        setVisibities(VISIBLE); // 显示文字

        // 切换背景颜色
        setBackgroundColor(Color.parseColor(defaultShadow));
        likeBack.setBackgroundResource(R.drawable.while_background);
        disBack.setBackgroundResource(R.drawable.yellow_background);

        // 重置帧动画
        imageLike.setBackground(null);
        imageLike.setBackgroundResource(R.drawable.animation_like);
        animLike = (AnimationDrawable) imageLike.getBackground();
      }
    });

    imageLike.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        type = 0;// 设置动画对象
        animBack();// 拉伸背景
        setVisibities(VISIBLE); // 显示文字

        // 切换背景颜色
        setBackgroundColor(Color.parseColor(defaultShadow));
        likeBack.setBackgroundResource(R.drawable.yellow_background);
        disBack.setBackgroundResource(R.drawable.while_background);

        // 重置帧动画
        imageDis.setBackground(null);
        imageDis.setBackgroundResource(R.drawable.animation_dislike);
        animDis = (AnimationDrawable) imageDis.getBackground();
      }
    });
  }

  //背景伸展动画
  private void animBack() {
    // 动画执行中不能点击
    imageDis.setClickable(false);
    imageLike.setClickable(false);

    final int max = Math.max(like * 4, disLike * 4);
    animatorBack = ValueAnimator.ofInt(5, max);

    animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        int margin = (int) animation.getAnimatedValue();
        Logger.i("onAnimationUpdate , margin : " + margin);
        LayoutParams paramsLike = (LayoutParams) imageLike.getLayoutParams();
        paramsLike.bottomMargin = margin;
        if (margin <= like * 4) {
          imageLike.setLayoutParams(paramsLike);
        }
        if (margin <= disLike * 4) {
          imageDis.setLayoutParams(paramsLike);
        }
      }
    });
    isClose = false;
    animatorBack.addListener(this);
    animatorBack.setDuration(500);
    animatorBack.start();
  }

  // 背景收回动画
  private void setBackUp() {
    final int max = Math.max(like * 4, disLike * 4);
    animatorBack = ValueAnimator.ofInt(max, 5);
    animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        int margin = (int) animation.getAnimatedValue();
        LayoutParams paramsLike = (LayoutParams) imageLike.getLayoutParams();
        paramsLike.bottomMargin = margin;
        if (margin <= like * 4) {
          imageLike.setLayoutParams(paramsLike);
        }
        if (margin <= disLike * 4) {
          imageDis.setLayoutParams(paramsLike);
        }
      }
    });
    animatorBack.addListener(this);
    animatorBack.setDuration(500);
    animatorBack.start();
  }

  public void objectY(View view) {
    ObjectAnimator animator =
        ObjectAnimator.ofFloat(view, "translationY", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
    animator.setRepeatMode(ObjectAnimator.RESTART);
    animator.setDuration(1500);
    animator.start();
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        setBackUp(); // 执行回弹动画
      }
    });
  }

  public void objectX(View view) {
    ObjectAnimator animator =
        ObjectAnimator.ofFloat(view, "translationX", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
    animator.setRepeatMode(ObjectAnimator.RESTART);
    animator.setDuration(1500);
    animator.start();

    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        setBackUp(); //执行回弹动画
      }
    });
  }

  @Override public void onAnimationStart(Animator animation) {

  }

  @Override public void onAnimationEnd(Animator animation) {
    // 重置帧动画
    animDis.stop();
    animLike.stop();

    // 关闭时不执行帧动画
    if (isClose) {
      // 收回后可以点击
      imageLike.setClickable(true);
      imageDis.setClickable(true);
      // 隐藏文字
      setVisibities(GONE);
      // 回复透明
      setBackgroundColor(Color.TRANSPARENT);
      return;
    }
    isClose = true;
    if (type == 0) {
      animLike.start();
      objectY(imageLike);
    } else {
      animDis.start();
      objectX(imageDis);
    }
  }

  @Override public void onAnimationCancel(Animator animation) {

  }

  @Override public void onAnimationRepeat(Animator animation) {

  }

  //dp转px
  public static int dip2px(Context context, float dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }
}
