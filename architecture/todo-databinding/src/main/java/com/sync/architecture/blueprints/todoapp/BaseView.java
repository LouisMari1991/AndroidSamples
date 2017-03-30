package com.sync.architecture.blueprints.todoapp;

/**
 * @Description:
 * @Author 罗顺翔
 * @date 2017年03月30日 16:32
 */
public interface BaseView<T> {

  void setPresenter(T presenter);
}
