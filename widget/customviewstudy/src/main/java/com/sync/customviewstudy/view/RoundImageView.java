package com.sync.customviewstudy.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.sync.customviewstudy.utils.ApiLevelHelper;

/**
 * Description: 圆形头像
 * Author：Mari on 2017-07-25 22:30
 * Contact：289168296@qq.com
 */
public class RoundImageView extends ImageView {

  public RoundImageView(Context context) {
    super(context);
  }

  public RoundImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void setImageDrawable(@Nullable Drawable drawable) {
    if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
      setClipToOutline(true);
      super.setImageDrawable(drawable);
    } else {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      RoundedBitmapDrawable roundedDrawable =
          RoundedBitmapDrawableFactory.create(getResources(), bitmapDrawable.getBitmap());
      roundedDrawable.setCircular(true);
      super.setImageDrawable(roundedDrawable);
    }
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
      return;
    }
    if (w > 0 && h > 0) {
      setOutlineProvider(new RoundOutlineProvider(Math.min(w, h)));
    }
  }
}
