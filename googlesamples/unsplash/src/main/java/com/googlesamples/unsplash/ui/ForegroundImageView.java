package com.googlesamples.unsplash.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import com.googlesamples.unsplash.R;

/**
 * Author：Administrator on 2016/9/2 0002 22:07
 * Contact：289168296@qq.com
 */
public class ForegroundImageView extends ImageView {

  private Drawable foreground;

  public ForegroundImageView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(R.styleable.ForegroundView);

    final Drawable d = a.getDrawable(R.styleable.ForegroundView_android_foreground);

    if (d != null) {
      setForeground(d);
    }
    a.recycle();
    setOutlineProvider(ViewOutlineProvider.BOUNDS);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (foreground != null) {
      foreground.setBounds(0, 0, w, h);
    }
  }

  /***
   * 自定义 View 时重写 hasOverlappingRendering 方法指定 View 是否有 Overlapping 的情况，提高渲染性能。
   */
  @Override public boolean hasOverlappingRendering() {
    return false;
  }

  /**
   * 如果你的视图子类显示他自己的可视化对象，他将要重写此方法并且为了显示可绘制返回true。
   * 此操作允许进行绘制时有动画效果。
   * 确认当重写从方法时，需调用父类相应方法。（注：即记得调用super.verifyDrawable(who)）
   */
  @Override protected boolean verifyDrawable(Drawable who) {
    return super.verifyDrawable(who) || (who == foreground);
  }

  @Override public void jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState();
    if (foreground != null) {
      /**
       * If this Drawable does transition animations between states,
       * ask that it immediately jump to the current state and skip any active animations.
       */
      foreground.jumpToCurrentState();
    }
  }

  @Override protected void drawableStateChanged() {
    super.drawableStateChanged();
    if (foreground != null && foreground.isStateful()) {
      foreground.setState(getDrawableState());
    }
  }

  /**
   * Returns the drawable used as the foreground of this view. The
   * foreground drawable, if non-null, is always drawn on top of the children.
   *
   * @return A Drawable or null if no foreground was set.
   */
  public Drawable getForeground() {
    return foreground;
  }

  /**
   * 提供一个Drawable的呈现在这个图像的内容
   * Supply a Drawable that is to be rendered on top of the contents of this ImageView
   *
   * @param drawable The Drawable to be draw on top of the ImageView
   */
  public void setForeground(Drawable drawable) {
    if (foreground != drawable) {
      if (foreground != null) {
        foreground.setCallback(null);
        unscheduleDrawable(foreground);
      }

      foreground = drawable;

      if (foreground != null) {
        foreground.setBounds(0, 0, getWidth(), getHeight());
        setWillNotDraw(false);
        foreground.setCallback(this);
        if (foreground.isStateful()) {
          foreground.setState(getDrawableState());
        }
      } else {
        setWillNotCacheDrawing(true);
      }
      invalidate();
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (foreground != null) {
      foreground.draw(canvas);
    }
  }

  @Override public void drawableHotspotChanged(float x, float y) {
    super.drawableHotspotChanged(x, y);
    if (foreground != null) {
      foreground.setHotspot(x, y);
    }
  }
}
