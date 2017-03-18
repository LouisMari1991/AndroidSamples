####ViewRoot和DecorView

　　ViewRoot 对应于 ViewRootImpl 类，它是连接 `WindowManager` 和 `DecorView` 的纽带， `View` 的三大流程均是通过 ViewRoot来完成的。 在 `ActivityThread` 中来完成的。 在 `ActivityThread` 中， 当 `Activity` 对象被创建完毕后，会将 `DecorView` 添加到 `Window` 中，同时会创建 `ViewRootImpl` 对象，并将 `ViewRootImpl` 对象和 `DecorView` 建立关联，这个过程可参看如下源码。


```
root = new ViewRootImpl(view.getContext(), display);
root.setView(view, wparams, panelParentView);	
```

　　View 的绘制流程是从 `ViewRoot` 的 `performTraversals` 方法开始的，它经过 `mesure` 、 `layout` 、 `draw` 三个过程才能最终将一个 View 绘制出来， 其中 `mesure` 用来测量 View 的宽和高， `layout` 用来确定 View 在父容器中放置的位置，而 `draw` 则负责将 View 绘制的屏幕上。针对 `performTraversals` 的大致流程如下图。

<img src="https://github.com/MariShunxiang/GitTrainning/blob/master/viewwork/viewwork1.png?raw=true" width="60%" height="60%" />

　　`performTraversals` 会依次调用 `performMesure`、`performLayout` 和 `performDraw` 三个方法，这三个方法分别完成顶级 View 的 `mesure`、`layout`、和 `draw` 这三大流程，其中在 `performMeasure` 中会调用 `measure` 方法， 在 `measure` 方法中又会调用 `onMeasure` 方法，在 `onMesure` 方法中则会对所有的子元素进行 `measure` 过程，这个时候 `measure` 流程就从父容器传递到子元素中了，这样就完成了一次 `measure` 过程。 接着子元素会重复父容器的 `measure` 过程，如此反复就完成了整个 View 树的遍历。 同理， `performLayout` 和 `performDraw` 的传递流程和 `performMeasure` 是类似的，唯一不同的是， `performDraw` 的传递过程是在 `draw` 方法中通过 `dispatchDraw` 来实现的，不过这并没有本质区别。



----------
####MeasureSpec
