package com.sync.finistwo.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.sync.finistwo.R;
import com.sync.finistwo.utils.MeasureUtil;

/**
 * Created by YH on 2017-03-15.
 */

public class CustomView extends View {

  private Paint mPaint;
  private Bitmap mBitmap;
  private int x, y;// 位图绘制时左上角的起点坐标

  public CustomView(Context context) {
    super(context);
    initPaint();
    initRes();
  }

  public CustomView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initPaint();
    initRes();
  }

  public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initPaint();
    initRes();
  }

  private void initPaint() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //ColorMatrix colorMatrix = new ColorMatrix(new float[]{
    //    0.33F, 0.59F, 0.11F, 0, 0,
    //    0.33F, 0.59F, 0.11F, 0, 0,
    //    0.33F, 0.59F, 0.11F, 0, 0,
    //    0, 0, 0, 1, 0,
    //});
    //mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

    // 设置颜色过滤
    // mul全称是colorMultiply意为色彩倍增，而add全称是colorAdd意为色彩添加
    mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
  }

  private void initRes() {
    // 获取位图
    mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.a);

        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         * 屏幕坐标x轴向左偏移位图一半的宽度
         * 屏幕坐标y轴向上偏移位图一半的高度
         */
    x = MeasureUtil.getScreenSize((Activity) getContext())[0] / 2 - mBitmap.getWidth() / 2;
    y = MeasureUtil.getScreenSize((Activity) getContext())[1] / 2 - mBitmap.getHeight() / 2;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 绘制位图
    canvas.drawBitmap(mBitmap, x, y, mPaint);
  }
}
