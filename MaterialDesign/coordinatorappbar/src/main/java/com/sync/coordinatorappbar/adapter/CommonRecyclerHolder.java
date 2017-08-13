package com.sync.coordinatorappbar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Description:
 * Author：Mari on 2017-08-08 20:37
 * Contact：289168296@qq.com
 */
public class CommonRecyclerHolder extends RecyclerView.ViewHolder {

  private SparseArray<View> views;
  private Context           context;

  public CommonRecyclerHolder(Context context, View itemView) {
    super(itemView);
    this.context = context;
    // 指定一个初始为8
    this.views = new SparseArray<>(8);
  }

  /**
   * 取得一个RecyclerHolder 对象
   *
   * @param context 上下文
   * @param itemView 子项
   * @return 返回一个 RecyclerHolder 对象
   */
  public static CommonRecyclerHolder getRecyclerHolder(Context context, View itemView) {
    return new CommonRecyclerHolder(context, itemView);
  }

  public SparseArray<View> getViews() {
    return this.views;
  }

  /**
   * 通过view的id获取对应的控件，如果没有则加入views中
   *
   * @param viewId 控件的id
   * @return 返回一个控件
   */
  public <T extends View> T getView(int viewId) {
    View view = views.get(viewId);
    if (view == null) {
      view = itemView.findViewById(viewId);
      views.put(viewId, view);
    }
    return (T) view;
  }

  /**
   * 设置字符串
   */
  public CommonRecyclerHolder setText(int viewId, String text) {
    TextView tv = getView(viewId);
    tv.setText(text);
    return this;
  }

  public CommonRecyclerHolder setTextColor(int viewId, int color) {
    TextView textView = getView(viewId);
    textView.setTextColor(color);
    return this;
  }

  /**
   * 设置图片
   */
  public CommonRecyclerHolder setImageResource(int viewId, int drawableId) {
    ImageView iv = getView(viewId);
    iv.setImageResource(drawableId);
    return this;
  }

  /**
   * 设置图片
   */
  public CommonRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap) {
    ImageView iv = getView(viewId);
    iv.setImageBitmap(bitmap);
    return this;
  }

  public CommonRecyclerHolder setOnRecyclerItemClickListener(int viewId, View.OnClickListener listener) {
    View view = getView(viewId);
    view.setOnClickListener(listener);
    return this;
  }
}

















