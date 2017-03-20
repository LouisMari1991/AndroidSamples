####ViewRoot和DecorView

　　ViewRoot 对应于 ViewRootImpl 类，它是连接 `WindowManager` 和 `DecorView` 的纽带， `View` 的三大流程均是通过 ViewRoot来完成的。 在 `ActivityThread` 中来完成的。 在 `ActivityThread` 中， 当 `Activity` 对象被创建完毕后，会将 `DecorView` 添加到 `Window` 中，同时会创建 `ViewRootImpl` 对象，并将 `ViewRootImpl` 对象和 `DecorView` 建立关联，这个过程可参看如下源码。


```
root = new ViewRootImpl(view.getContext(), display);
root.setView(view, wparams, panelParentView);
```

　　View 的绘制流程是从 `ViewRoot` 的 `performTraversals` 方法开始的，它经过 `mesure` 、 `layout` 、 `draw` 三个过程才能最终将一个 View 绘制出来， 其中 `mesure` 用来测量 View 的宽和高， `layout` 用来确定 View 在父容器中放置的位置，而 `draw` 则负责将 View 绘制的屏幕上。针对 `performTraversals` 的大致流程如下图。

<img src="https://github.com/MariShunxiang/GitTrainning/blob/master/viewwork/viewwork1.png?raw=true" width="50%" height="50%" />

　　`performTraversals` 会依次调用 `performMesure`、`performLayout` 和 `performDraw` 三个方法，这三个方法分别完成顶级 View 的 `mesure`、`layout`、和 `draw` 这三大流程，其中在 `performMeasure` 中会调用 `measure` 方法， 在 `measure` 方法中又会调用 `onMeasure` 方法，在 `onMesure` 方法中则会对所有的子元素进行 `measure` 过程，这个时候 `measure` 流程就从父容器传递到子元素中了，这样就完成了一次 `measure` 过程。 接着子元素会重复父容器的 `measure` 过程，如此反复就完成了整个 View 树的遍历。 同理， `performLayout` 和 `performDraw` 的传递流程和 `performMeasure` 是类似的，唯一不同的是， `performDraw` 的传递过程是在 `draw` 方法中通过 `dispatchDraw` 来实现的，不过这并没有本质区别。

　　`measure` 过程决定了 View 的宽/高, `Measure` 完成以后，可以通过 `getMeasuredWidth` 和 `getMeasuredHeight` 方法来获取到 View 测量后的宽/高，在几乎所有的情况下它都等同于 View 最终的宽/高，但是特殊情况除外。 `Layout` 过程决定了 View 的四个顶点的坐标和实际的 View 的宽/高，完成以后，可以通过 `getTop`、 `getBottom` 、 `getLeft` 和 `getRight` 来拿到 View 四个顶点的位置，并可以通过 `getWidth` 和 `getHeight` 方法来拿到 View 最终宽/高。 `Draw` 过程决定了 View 的显示，只有 `draw` 方法完成以后 View 的内容才能呈现在屏幕上。

　　`DecorView` 作为顶级 View, 一般情况下它内部会包含一个竖直方向的 `LinearLayout`, 在这个 `LinearLayout` 里面有上下两个部分，上面是标题栏，下面是内容栏。在 `Activity` 中我们通过 `setContentView` 所设置的布局文件其实就是被加载到内容栏之中的，而内容栏的 `id` 是 `content`, 因此可以理解为 `Activity` 指定布局的方法不再 `setView` 而叫 `setContentView` ， 因为我们的布局的确是加到了 `id` 为 `content` 的 `FrameLayout` 中。如何得到 `content` 呢？ 可以这样： `ViewGroup content = findViewById(R.android.id.content)` 。如何得到我们设置的 `View` 呢？ 可以这样： `content.getChilldAt(0)` 。 同时，通过源码我们可以知道， `DecorView` 其实是一个 `FrameLayout` ， `View` 层的事件都先经过 `DecorView` , 然后才传递给我们的 `View` 。

<img src="https://raw.githubusercontent.com/MariShunxiang/GitTrainning/master/viewwork/viewwork2.bmp" width="30%" height="30%" />

<br/>

###理解 MeasureSpec
----------

　　为了更好地理解 `View` 的测量过程，我们还需要理解 `MeasureSpec`,通过源码可以发现， `MeasureSpec` 参与了 `View` 的 `mesure` 过程, `MeasureSpec` 很大程度上决定了一个 `View` 的尺寸规格，之所以这么说是因为这个过程还受父容器的影响，因为父容器影响 `View` 的 `MeasureSpec` 的创建过程。 在测量过程中，系统将 `View` 的 `LayoutParams` 根据父容器所施加的规则转换成对应的 `MeasureSpec` ，然后再根据这个　`MeasureSpec` 来测量 `View` 的宽/高。

####MeasureSpec

　　`MeasureSpec` 代表一个32位的 int 值，高2位代表 `SpecMode`, 低30位代表 `SpecSize` , `SpecMode` 是指测量模式， 而 `SpecSize` 是指某种测量模式下的规格大小。 下面先看一下 `MeasureSpec` 内部的一些常量的定义，通过下面的代码，应该不难理解 `MeasureSpec` 的工作原理：

```
private static final int MODE_SHIFT = 30;
private static final int MODE_MASK = 0x3 << MODE_SHIFT;
public static final int UNSPECIFIED = 0 << MODE_SHIFT;
public static final int EXACTLY = 1 << MODE_SHIFT;
public static final int AT_MOST = 2 << MODE_SHIFT;

public static int makeMeasureSpec(int size, int mode) {
  if (sUserBrokenMakeMeasureSpec) {
    return size + mode;
  } else {
    return (size & ~MODE_MASK) | (mode & MODE_MASK);
  }
}

public static int getMode(int measureSpec) {
  return (measureSpec & MODE_MASK);
}

public static int getSize(int measureSpec) {
  return (measureSpec & ~MODE_MASK);
}
```

　　`measureSpec` 通过将 `SpecMode` 和 `SpecSize` 打包成一个 int 值来避免过的的内存分配，为了方便操作，其提供了打包和解包的方法。 `SpecMode` 和 `SpecSize` 也是一个 int 值，一组 `SpecMode` 和 `SpecSize` 可以打包为一个 `MeasureSpec` , 而一个 `MeasureSpec` 可以通过解包的形式来得出其原始的 `SpecMode` 和 `SpecSize` , 需要注意的是这里提到的 `MeasureSpec` 是指 `MeasureSpec` 所代表的 int 值，而非 `MeasureSpec` 本身。

　　`SpecMode` 有三类，每一类都表示特殊的含义，如下所示：

>* UNSPECIFIED : 父容器不对 `View` 有任何限制，要多大给多大，这种情况一般用于系统内部，表示一种测量状态。
>* EXACLY : 父容器已经检测出 `View` 所需要的精确大小，这个时候 `View` 的最终大小就是 `SpecSize` 所指定的值，它对应于 `LayoutParams` 中的 `match_parent` 和 具体的数值这两种模式。
>* AT_MOST : 父容器指定了一个可用大小即 `SpecSize` , `View` 的大小不能大于这个值，具体是什么值要看不同 `View` 的具体实现，它对应于 `LayoutParams` 中的 `warp_content` 。
