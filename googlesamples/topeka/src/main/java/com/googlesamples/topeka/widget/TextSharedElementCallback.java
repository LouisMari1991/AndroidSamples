package com.googlesamples.topeka.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import com.googlesamples.topeka.helper.ViewUtils;
import com.sync.logger.Logger;
import java.util.List;

/**
 * This callback allows a shared TextView to resize text and start padding during transition.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP) public class TextSharedElementCallback extends SharedElementCallback {

  private final int   mInitialPaddingStart;
  private final float mInitialTextSize;
  private       float mTargetViewTextSize;
  private       int   mTargetViewPaddingStart;
  private static final String TAG = "TextResize";

  public TextSharedElementCallback(int initialPaddingStart, float initialTextSize) {
    mInitialPaddingStart = initialPaddingStart;
    mInitialTextSize = initialTextSize;
  }

  @Override public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
      List<View> sharedElementSnapshots) {
    TextView targetView = getTextView(sharedElements);
    if (targetView == null) {
      Logger.w("onSharedElementStart: No shared TextView, skipping.");
      return;
    }
    mTargetViewTextSize = targetView.getTextSize();
    mTargetViewPaddingStart = targetView.getPaddingStart();
    // Setup the TextView's start values.
    targetView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInitialTextSize);
    ViewUtils.setPaddingStart(targetView, mInitialPaddingStart);
  }

  @Override public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements,
      List<View> sharedElementSnapshots) {
    TextView initialView = getTextView(sharedElements);

    if (initialView == null) {
      Logger.w("onSharedElementEnd: No shared TextView, skipping.");
      return;
    }

    // Setup the TextView's end values.
    initialView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTargetViewTextSize);
    ViewUtils.setPaddingStart(initialView, mTargetViewPaddingStart);

    // Re-measure the TextView (since the text size has changed).
    int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    initialView.measure(widthSpec, heightSpec);
    initialView.requestLayout();
  }

  @Nullable private TextView getTextView(List<View> sharedElements) {
    TextView targetView = null;
    for (int i = 0; i < sharedElements.size(); i++) {
      if (sharedElements.get(i) instanceof TextView) {
        targetView = (TextView) sharedElements.get(i);
      }
    }
    return targetView;
  }
}
