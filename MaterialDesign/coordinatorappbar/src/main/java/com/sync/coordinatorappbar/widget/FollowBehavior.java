package com.sync.coordinatorappbar.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 自定义CoordinatorLayout的Behavior,泛型为观察者View(要跟着别人动的那个)
 *
 * 必须重写两个方法: layoutDependOn 和 onDependentViewChanged
 *
 * Description:
 * Author：Mari on 2017-08-07 22:55
 * Contact：289168296@qq.com
 */
public class FollowBehavior extends CoordinatorLayout.Behavior<TextView> {

  /**
   * 构造方法
   */
  public FollowBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 判断 child 的布局是否依赖 dependency
   *
   * 根据逻辑来判断返回值,返回 false 表示不依赖,返回 true 表示依赖
   *
   * 在一个交互行为中, dependent view 的变化决定了另一个相关 View 的行为.
   * 在这个例子中, Button 就是 dependent view, 因为 TextView 跟随着它.
   * 实际上 dependent view 就相当与我们前面介绍的被观察者
   */
  @Override public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
    return dependency instanceof Button;
  }

  @Override public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
    child.setX(dependency.getX());
    child.setY(dependency.getY() + 100);
    return true;
  }
}
