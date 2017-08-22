package com.sync.customviewstudy.viewgroup;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sync.customviewstudy.R;

/**
 * Description:
 * Author：Mari on 2017-08-20 21:45
 * Contact：289168296@qq.com
 */
public class SmileView extends LinearLayout {

  public SmileView(Context context) {
    this(context, null);
  }

  public SmileView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SmileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  int dividerMargin = 20; // 分割线间距

  String defaultLick = "喜欢";
  String defaultDis  = "无感";

  int defaultBottom    = 70;
  int defaultTextColor = Color.WHITE;
  int defaultGravity   = Gravity.CENTER_HORIZONTAL;
  int defaultSize      = dip2px(getContext(), 25);

  String defaultShadow = "#7F484848";

  int lick    = 10; // 点赞数
  int disLike = 20; // 差评数

  float fLike, fDis;

  ImageView imageLike;
  ImageView imageDis;

  AnimationDrawable animLike; // 笑脸帧动画
  AnimationDrawable animDis; // 哭脸帧动画

  ValueAnimator animatorBack; // 背景拉伸动画

  TextView likeNum, disNum, likeText, disText;
  LinearLayout likeBack, disBack, likeAll, disAll;

  int type = 0;// 选择执行帧动画的笑脸, 0 : 笑脸, 1 : 哭脸
  boolean isClose;// 判断首期动画

  private void init() {
    this.removeAllViews();
    // 初始化总布局
    setOrientation(HORIZONTAL);
    setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    setBackgroundColor(Color.TRANSPARENT); // 开始透明

    // 设置百分比
    float count = lick + disLike;
    fLike = lick / count;
    fDis = disLike / count;
    lick = (int) (fDis * 100);
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
    likeNum.setText(lick + "%");
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




  }

  //dp转px
  public static int dip2px(Context context, float dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }
}
