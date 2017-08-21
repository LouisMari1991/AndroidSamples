package com.sync.customviewstudy.viewgroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

  int lick    = 10; // 点赞数
  int disLick = 20; // 差评数

  float fLike, fDis;

  ImageView imageLike;
  ImageView imageDis;

  AnimationDrawable animLike; // 笑脸帧动画
  AnimationDrawable animDis; // 哭脸帧动画



  private void init() {
    this.removeAllViews();
    // 初始化总布局
    setOrientation(HORIZONTAL);
    setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    setBackgroundColor(Color.TRANSPARENT); // 开始透明

    // 设置百分比
    float count = lick + disLick;
    fLike = lick / count;
    fDis = disLick / count;
    lick = (int) (fDis * 100);
    disLick = (int) (fDis * 100);

    // 初始化图片
    imageLike = new ImageView(getContext());
    // 添加动画资源, 或者帧动画
    imageLike.setBackgroundResource(R.drawable.animation_like);
  }
}
