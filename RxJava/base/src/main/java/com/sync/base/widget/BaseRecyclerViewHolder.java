package com.sync.base.widget;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by YH on 2017-01-16.
 */

public abstract class BaseRecyclerViewHolder<T, D extends ViewDataBinding> extends RecyclerView.ViewHolder {

  public D binding;

  public BaseRecyclerViewHolder(ViewGroup viewGroup, @LayoutRes int layoutId) {
    super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false).getRoot());
    binding = DataBindingUtil.getBinding(itemView);
  }

  public void onBaseBindViewHolder(T t, final int position) {
    onBindViewHolder(t, position);
    binding.executePendingBindings();
  }

  protected abstract void onBindViewHolder(T t, int position);
}
