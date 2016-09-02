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

  public void setForeground(Drawable drawable) {
    if (foreground != drawable) {
      if (foreground != null) {
        foreground.setBounds(0, 0, getWidth(), getHeight());
        setWillNotDraw(false);
        foreground.setCallback(null);
        foreground.setCallback(this);
        if (foreground.isStateful()) {
          foreground.setState(getDrawableState());
        }
      } else {
        setWillNotDraw(true);
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
