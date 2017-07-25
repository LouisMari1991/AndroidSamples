package com.googlesamples.topeka.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import com.googlesamples.topeka.R;
import com.googlesamples.topeka.helper.ApiLevelHelper;
import com.googlesamples.topeka.widget.outlineprovider.RoundOutlineProvider;

/**
 * A simple view that wraps an avatar.
 * Created by Administrator on 2016/8/18 0018.
 */
public class AvatarView extends ImageView implements Checkable {

  private boolean mChecked;
  private static final int NOT_FOUND = 0;

  public AvatarView(Context context) {
    this(context, null);
  }

  public AvatarView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarView, defStyleAttr, 0);
    try {
      final int avatarDrawableId = a.getResourceId(R.styleable.AvatarView_avatar, NOT_FOUND);
      if (avatarDrawableId != NOT_FOUND) {
        setAvatar(avatarDrawableId);
      }
    } finally {
      a.recycle();
    }
  }

  @Override public void setChecked(boolean b) {
    mChecked = b;
    invalidate();
  }

  @Override public boolean isChecked() {
    return mChecked;
  }

  @Override public void toggle() {
    setChecked(!mChecked);
  }

  @SuppressLint("NewApi") public void setAvatar(@DrawableRes int resId) {
    if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
      setClipToOutline(true);  //为了裁剪一个可绘制的视图形状，需要先设置一个outline然后调用View.setClipToOutline方法
      setImageResource(resId);
    } else {
      setAvatarPreLollipop(resId);
    }
  }

  private void setAvatarPreLollipop(@DrawableRes int redId) {
    Drawable drawable = ResourcesCompat.getDrawable(getResources(), redId, getContext().getTheme());
    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
    @SuppressLint("ConstantConditions") RoundedBitmapDrawable roundedBitmapDrawable =
        RoundedBitmapDrawableFactory.create(getResources(), bitmapDrawable.getBitmap());
    roundedBitmapDrawable.setCircular(true);
    setImageDrawable(roundedBitmapDrawable);
  }

  @Override protected void onDraw(@NonNull Canvas canvas) {
    super.onDraw(canvas);
    // 画选中的圆弧
    if (mChecked) {
      Drawable border = ContextCompat.getDrawable(getContext(), R.drawable.selector_avatar);
      border.setBounds(0, 0, getWidth(), getHeight());
      border.draw(canvas);
    }
  }

  /**
   * onLayout() 之后调用，在5.0以上系统会设置圆角Icon.
   */
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





























