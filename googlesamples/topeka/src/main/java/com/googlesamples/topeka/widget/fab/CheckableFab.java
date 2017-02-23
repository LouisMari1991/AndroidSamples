package com.googlesamples.topeka.widget.fab;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * Created by YH on 2017-02-23.
 */

public class CheckableFab extends FloatingActionButton implements Checkable {
  public CheckableFab(Context context) {
    super(context);
  }

  public CheckableFab(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CheckableFab(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void setChecked(boolean checked) {
    
  }

  @Override public boolean isChecked() {
    return false;
  }

  @Override public void toggle() {

  }
}
