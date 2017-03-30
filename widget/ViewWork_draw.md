## View 的工作流程
　　View 的工作流程只要是指 `measure` `layout` `draw` 三大流程，即测量、布局和绘制。其中 `measure` 确定 View 的测量宽/高， `layout` 确定 View 的最终宽/高和四个顶点的位置，而 `draw` 则将 View 绘制到屏幕上。

#### draw 过程


　　Draw 过程就比较简单了，它的作用是将 `View` 绘制到屏幕上面。 `View` 的绘制过程遵循如下几步：

  1. 绘制背景 `background.draw(canvas)`

  2. 绘制自己 `onDraw`

  3. 绘制 child `dispatchDraw`

  4. 绘制装饰 `onDrawScrollBars`

　　这一点通过 `draw` 方法的源码可以明显的看出来，如下所示：

```java
public void draw(Canvas canvas) {
    final int privateFlags = mPrivateFlags;
    final boolean dirtyOpaque = (privateFlags & FLAG_DIRTY_MASK) ==
      PFLAG_DIRTY_OPAQUE &&
        (mAttachInfo == null || !mAttachInfo.mIgnoreDirtyState);
    mPrivateFlags = (privateFlags & ~PFLAG_DIRTY_MASK) | PFLAG_DRAWN;
}

 /*
  * Draw traversal performs several drawing steps which must be executed in
  * the appropriate order;
  *
  * 1. Draw the background
  * 2. If necessary, save the canvas' layers to prepare for fading
  * 3. Draw view's content
  * 4. Draw children
  * 5. If necessary, draw the fading edges and restore layers
  * 6. Draw decorations (scrollbars for instance)
  */

  // Step 1, draw the background, if needed
  int saveCount;

  if  (!dirtyOpaque) {
    drawbackground(canvas);
  }

  // skip step 2 & 5 if possible (common case)
  final int viewFlags = mViewFlags;
  boolean horizontalEdges = (viewFlags & FADING_EDGE_HORIZONTAL) != 0;
  boolean verticalEdges = (viewFlags & FADING_EDGE_VERTICAL) != 0;
  if (!verticalEdges && !horizontalEdges) {
    // Step 3, draw the content
    if (!dirtyOpaque) {
      onDraw(canvas);
    }

    // Setp 4, draw the children
    dispatchDraw(canvas);

    // Step 6, draw decorations (scrollbars)
    onDrawScrollBars(canvas);

    if (mOverlay != null && !mOverlay.isEmpey()) {
      mOverlay.getOverlayView().dispatchDraw(canvas);
    }

    // we're done...
    return;
  }
  ....
}
```
　　`View` 绘制过程的传递是通过 `dispatchDraw` 来实现的， `dispatchDraw` 会遍历调用所有子元素的 `draw` 方法，如此 `draw` 事件就一层层地传递了下去。 `View` 有一个特殊的方法， `setWillNotDraw` ，先看一下它的源码，如下图所示：
```java
  /*
   * If this view does'n do any drawing on its own, set this flags to
   * allow further optimizations. By default, this flag is not set on
   * View, but could be set on some View subclasses such as ViewGroup.
   *
   * Typically, if you override {@link #onDraw(android.graphics.Canvas)}
   * you should clear this flag.
   *
   * @param willNotDraw whether or not this view draw on its own
   */
   public void setWillNotDraw(boolean) {
     setFlags(willNotDraw ? WILL_NOT_DRAW : 0, DRAW_MASK);
   }

```
　　从 `setWillNotDraw` 这个方法的注释可以看出，如果一个 `View` 不需要绘制任何内容，那么设置这个标记位为 `true` 以后，系统会进行相应的优化。默认情况下， `View` 没有启用这个优化标记位，但是 `ViewGroup` 会默认启用这个优化标记位。这个标记位对实际开发的意义是： 当我们的自定义空间继承于 `ViewGroup` 并且本身不具备绘制功能时，就可以开启这个标记位从而便于系统进行后续的优化。当然，当明确知道一个 `ViewGroup` 需要通过 `onDraw` 来绘制内容时，我们需要显式的关闭 `WILL_NOT_DRAW` 这个标记位。
