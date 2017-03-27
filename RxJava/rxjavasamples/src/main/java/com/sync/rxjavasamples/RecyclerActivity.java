package com.sync.rxjavasamples;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.orhanobut.logger.Logger;
import com.sync.rxjavasamples.util.ReflectUtil;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Author：SYNC on 2017/3/27 0027 19:59
 * Contact：289168296@qq.com
 */
public class RecyclerActivity extends BaseActivity {

  @Bind(R.id.recycler) RecyclerView recycler;

  @Bind(R.id.result) TextView result;

  @Bind(R.id.progressBar) ProgressBar progressBar;

  List<Method> getMethods() {
    List<Method> result = new ArrayList<>();
    Method[] reflects = ReflectUtil.getMethodNames(this.getClass());
    for (Method method : reflects) {
      if (!method.getName().contains("$") && Modifier.isPublic(method.getModifiers())) {
        result.add(method);
      }
    }
    Collections.sort(result, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));
    return result;
  }

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setContentView(R.layout.activity_base);
    setUpRecyclerView();
  }

  void setUpRecyclerView() {
    List<Method> methods = getMethods();
    CommonAdapter commonAdapter = new CommonAdapter<Method>(this, R.layout.recycler_view_tv_item, methods) {
      @Override public void convert(ViewHolder holder, Method m) {
        holder.setText(R.id.text, m.getName());
      }
    };

    commonAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override public void onItemClick(ViewGroup parent, View view, Object o, int position) {
        ReflectUtil.invokeMethod(RecyclerActivity.this, methods.get(position).getName());
      }

      @Override public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
      }
    });

    recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    recycler.setAdapter(commonAdapter);
  }

  public void logger(Object obj) {
    Logger.d(String.valueOf(obj));
    Observable.just(obj).subscribeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
      result.setText(result.getText() + "\n" + o);
    });
  }

  @OnClick(R.id.clear) public void clear() {
    result.setText("");
  }

  @OnClick(R.id.unsubscribe) public void unsubscribeClick() {
    unsubscribe();
  }
}


















