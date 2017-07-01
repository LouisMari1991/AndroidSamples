# Android 中 NestedScrollingParent 接口

#### 接口（android.support.v4.view.NestedScrollingParent）


> Nested : 嵌套的

几个实现类 | 介绍
----|------
CoordinatorLayout | 非常强大，协调它里面 View 的嵌套滑动  
NestedScrollView | 类似 ScrollView, 但是支持嵌套滑动的
SwipeRefreshLayout | SwipeRefreshLayout 支持竖直方向的下拉刷新控件

#### 方法介绍

```java
  **
   当你希望自己的自定义布局支持嵌套子视图并且处理滚动操作，就可以实现该接口。
   实现这个接口后可以创建一个 NestedScrollingParentHelper 字段，使用它来帮助你处理大部分的方法。
   处理嵌套的滚动时应该使用  `ViewCompat`，`ViewGroupCompat`或`ViewParentCompat` 中的方法来处理，这是一些兼容库，
   他们保证 Android 5.0之前的兼容性垫片的静态方法，这样可以兼容 Android 5.0 之前的版本。
   */
   public interface NestedScrollingParent {
    /**
     * 当子视图调用 startNestedScroll(View, int) 后调用该方法。返回 true 表示响应子视图的滚动。
     * 实现这个方法来声明支持嵌套滚动，如果返回 true，那么这个视图将要配合子视图嵌套滚动。当嵌套滚动结束时会调用到 onStopNestedScroll(View)。
     *
     * @param child 可滚动的子视图
     * @param target NestedScrollingParent 的直接可滚动的视图，一般情况就是 child
     * @param nestedScrollAxes 包含 ViewCompat#SCROLL_AXIS_HORIZONTAL, ViewCompat#SCROLL_AXIS_VERTICAL 或者两个值都有。
     * @return 返回 true 表示响应子视图的滚动。
     */
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes);

    /**
     * 如果 onStartNestedScroll 返回 true ，然后走该方法，这个方法里可以做一些初始化。
     */
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes);

    /**
     * 子视图开始滚动前会调用这个方法。这时候父布局（也就是当前的 NestedScrollingParent 的实现类）可以通过这个方法来配合子视图同时处理滚动事件。
     *
     * @param target 滚动的子视图
     * @param dx 绝对值为手指在x方向滑动的距离，dx<0 表示手指在屏幕向右滑动
     * @param dy 绝对值为手指在y方向滑动的距离，dy<0 表示手指在屏幕向下滑动
     * @param consumed 一个数组，值用来表示父布局消耗了多少距离，未消耗前为[0,0], 如果父布局想处理滚动事件，就可以在这个方法的实现中为consumed[0]，consumed[1]赋值。
     *                 分别表示x和y方向消耗的距离。如父布局想在竖直方向（y）完全拦截子视图，那么让 consumed[1] = dy，就把手指产生的触摸事件给拦截了，子视图便响应不到触摸事件了 。
     */
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed);

  /**
     * 这个方法表示子视图正在滚动，并且把滚动距离回调用到该方法，前提是 onStartNestedScroll 返回了 true。
     * <p>Both the consumed and unconsumed portions of the scroll distance are reported to the
     * ViewParent. An implementation may choose to use the consumed portion to match or chase scroll
     * position of multiple child elements, for example. The unconsumed portion may be used to
     * allow continuous dragging of multiple scrolling or draggable elements, such as scrolling
     * a list within a vertical drawer where the drawer begins dragging once the edge of inner
     * scrolling content is reached.</p>
     *
     * @param target 滚动的子视图
     * @param dxConsumed 手指产生的触摸距离中，子视图消耗的x方向的距离
     * @param dyConsumed 手指产生的触摸距离中，子视图消耗的y方向的距离 ，如果 onNestedPreScroll 中 dy = 20， consumed[0] = 8，那么 dy = 12
      * @param dxUnconsumed 手指产生的触摸距离中，未被子视图消耗的x方向的距离
     * @param dyUnconsumed 手指产生的触摸距离中，未被子视图消耗的y方向的距离
     */
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,int dxUnconsumed, int dyUnconsumed);

    /**
     * 响应嵌套滚动结束
     *
     * 当一个嵌套滚动结束后（如MotionEvent#ACTION_UP， MotionEvent#ACTION_CANCEL）会调用该方法，在这里可有做一些收尾工作，比如变量重置
     */
    public void onStopNestedScroll(View target);

    /**
     * 手指在屏幕快速滑触发Fling前回调，如果前面 onNestedPreScroll 中父布局消耗了事件，那么这个也会被触发
     * 返回true表示父布局完全处理 fling 事件
     *
     * @param target 滚动的子视图
     * @param velocityX x方向的速度（px/s）
     * @param velocityY y方向的速度
     * @return true if this parent consumed the fling ahead of the target view
     */
    public boolean onNestedPreFling(View target, float velocityX, float velocityY);

    /**
     * 子视图fling 时回调，父布局可以选择监听子视图的 fling。
     * true 表示父布局处理 fling，false表示父布局监听子视图的fling
     *
     * @param target View that initiated the nested scroll
     * @param velocityX Horizontal velocity in pixels per second
     * @param velocityY Vertical velocity in pixels per second
     * @param consumed true 表示子视图处理了fling
     */
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed);

    /**
     * 返回当前 NestedScrollingParent 的滚动方向，
     *
     * @return
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL
     * @see ViewCompat#SCROLL_AXIS_VERTICAL
     * @see ViewCompat#SCROLL_AXIS_NONE
     */
    public int getNestedScrollAxes();
}
```

#### 接口（android.support.v4.view.NestedScrollingChild）

```
public interface NestedScrollingChild {  
    /** 
     * 设置嵌套滑动是否能用
     * 
     *  @param enabled true to enable nested scrolling, false to disable
     */  
    public void setNestedScrollingEnabled(boolean enabled);  

    /** 
     * 判断嵌套滑动是否可用 
     * 
     * @return true if nested scrolling is enabled
     */  
    public boolean isNestedScrollingEnabled();  

    /** 
     * 开始嵌套滑动
     * 
     * @param axes 表示方向轴，有横向和竖向
     */  
    public boolean startNestedScroll(int axes);  

    /** 
     * 停止嵌套滑动 
     */  
    public void stopNestedScroll();  

    /** 
     * 判断是否有父View 支持嵌套滑动 
     * @return whether this view has a nested scrolling parent
     */  
    public boolean hasNestedScrollingParent();  

    /** 
     * 在子View的onInterceptTouchEvent或者onTouch中，调用该方法通知父View滑动的距离
     *
     * @param dx  x轴上滑动的距离
     * @param dy  y轴上滑动的距离
     * @param consumed 父view消费掉的scroll长度
     * @param offsetInWindow   子View的窗体偏移量
     * @return 支持的嵌套的父View 是否处理了 滑动事件 
     */  
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow);  

    /** 
     * 子view处理scroll后调用
     *
     * @param dxConsumed x轴上被消费的距离（横向） 
     * @param dyConsumed y轴上被消费的距离（竖向）
     * @param dxUnconsumed x轴上未被消费的距离 
     * @param dyUnconsumed y轴上未被消费的距离 
     * @param offsetInWindow 子View的窗体偏移量
     * @return  true if the event was dispatched, false if it could not be dispatched.
     */  
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,  
          int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow);  



    /** 
     * 滑行时调用 
     *
     * @param velocityX x 轴上的滑动速率
     * @param velocityY y 轴上的滑动速率
     * @param consumed 是否被消费 
     * @return  true if the nested scrolling parent consumed or otherwise reacted to the fling
     */  
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);  

    /** 
     * 进行滑行前调用
     *
     * @param velocityX x 轴上的滑动速率
     * @param velocityY y 轴上的滑动速率 
     * @return true if a nested scrolling parent consumed the fling
     */  
    public boolean dispatchNestedPreFling(float velocityX, float velocityY);  
}
```