package com.sync.architecture.blueprints.todoapp;

/**
 * Description:
 * Author：SYNC on 2017/6/1 0001 22:37
 * Contact：289168296@qq.com
 */
public interface BaseView<T> {
  void setPresenter(T presenter);
}
