package com.sync.dragrecyclerview.drag;

import android.view.View;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年04月05日 14:11
 */
public interface RecycleCallBack {

  void onItemClick(int position, View view);

  void onItemMove(int from, int to);
}
