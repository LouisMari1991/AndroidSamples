package com.googlesamples.unsplash.ui.grid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author：Administrator on 2016/9/2 0002 20:37
 * Contact：289168296@qq.com
 */
public abstract class OnItemSelectedListener implements RecyclerView.OnItemTouchListener{

  private final GestureDetector mGestureDetector;

  public OnItemSelectedListener(Context context){
   mGestureDetector = new GestureDetector(context,
       new GestureDetector.SimpleOnGestureListener(){

         @Override public boolean onSingleTapUp(MotionEvent e) {
           return true;
         }
       });
  }

  public abstract void onItemSelected(RecyclerView.ViewHolder holder, int position);

  @Override public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
    if (mGestureDetector.onTouchEvent(e)) {
      View touchedView = rv.findChildViewUnder(e.getX(), e.getY());
      onItemSelected(rv.findContainingViewHolder(touchedView),
          rv.getChildAdapterPosition(touchedView));
    }
    return false;
  }

  @Override public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
