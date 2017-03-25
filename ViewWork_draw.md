### View 的工作流程
　　View 的工作流程只要是指 `measure` `layout` `draw` 三大流程，即测量、布局和绘制。其中 `measure` 确定 View 的测量宽/高， `layout` 确定 View 的最终宽/高和四个顶点的位置，而 `draw` 则将 View 绘制到屏幕上。

#### draw 过程


　　Draw 过程就比较简单了，它的作用是将 `View` 绘制到屏幕上面。 `View` 的绘制过程遵循如下几步：

  1. 绘制背景 `background.draw(canvas)`

  2. 绘制自己 `onDraw`

  3. 绘制 child `dispatchDraw`

  4. 绘制装饰 `onDrawScrollBars`
