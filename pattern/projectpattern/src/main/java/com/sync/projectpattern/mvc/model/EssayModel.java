package com.sync.projectpattern.mvc.model;

import android.content.Context;
import com.sync.projectpattern.mvc.bean.Essay;
import java.util.ArrayList;
import java.util.List;

/**
 * Author：Administrator on 2017/1/14 0014 12:03
 * Contact：289168296@qq.com
 */
public class EssayModel {
  public interface OnEssayListener {
    void onSuccess(List<Essay> list);

    void onError();
  }

  private Context mContext;

  private OnEssayListener mOnEssayListener;

  public EssayModel(Context context) {
    this.mContext = context;
  }

  public void getEssay(int num, OnEssayListener listener) {
    this.mOnEssayListener = listener;
    List<Essay> list = new ArrayList<>();
    Essay essay = new Essay();
    if (num != 0) {
      essay.setTitle("更新的title");
    } else {
      essay.setTitle("传入的为0");
    }
    list.add(essay);
    mOnEssayListener.onSuccess(list);
  }
}
