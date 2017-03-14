####ViewRoot和DecorView

　　ViewRoot 对应于 ViewRootImpl 类，它是连接 `WindowManager` 和 `DecorView` 的纽带， `View` 的三大流程均是通过 ViewRoot来完成的。 在 `ActivityThread` 中来完成的。 在 `ActivityThread` 中， 当 `Activity` 对象被创建完毕后，会将 `DecorView` 添加到 `Window` 中，同时会创建 `ViewRootImpl` 对象，并将 `ViewRootImpl` 对象和 `DecorView` 建立关联，这个过程可参看如下源码。


```
root = new ViewRootImpl(view.getContext(), display);
root.setView(view, wparams, panelParentView);	
```

----------
####MeasureSpec
