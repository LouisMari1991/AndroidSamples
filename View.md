####View位置参数
 　　View的位置主要是由它的四个顶点来决定，分别对应于View的四个属性：top,left,right,bottom, 其中top是左上角纵坐标，left是左上角横坐标，right是右下角横坐标，bottom是右下角总坐标。需要注意的是，这些坐标都是相对于View的父容器来说的，因此它是一种相对坐标。在Android中x轴和y轴的正方向分别为右和下。

在View的源码中view的四个坐标对应于四个成员变量:mLeft,mRight,mTop,mBottom,获取方式如下：

```
Left = getLeft();
Right = getRight();
Top = getTop();
Bottom = getBottom();
```
 　　
从Android3.0开始，View增加几个额外的参数：x,y,translationX,translationY, 其中x和y是View左上角的坐标，而translationX和translationY是View左上角相对于父容器的偏移量。这几个参数也是相对于父容器的坐标，并且translationX和translationY的默认值是0，和View的四个基本位置参数一样，View也为它们提供了get/set方法，这几个参数的换算关系如下所示：
```
x = left + translationX
y = top + translationY
```

　　需要注意的是，在View平移的过程中，`top`和`left`表示的是原始左上角的位置信息，其值并不会发生改变，此时发生改变的是`x`,`y`,`translationX`,`translationY`这四个参数

----------
###View getWidth()和getMeasuredWidth()

![这里写图片描述](http://s12.sinaimg.cn/middle/6e519585gbdc2eebf9fab&690)

　　getMeasuredWidth()获取的是view原始的大小，也就是这个view在XML文件中配置或者是代码中设置的大小。getWidth（）获取的是这个view最终显示的大小，这个大小有可能等于原始的大小也有可能不等于原始大小。


----------
###View滑动的三种常用方式

>* scrollTo/scrollBy:操作简单，适合对View内容的滑动；
>* 动画：操作简单，主要适用于没有交互的View和实现复杂的动画效果；
>* 改变布局参数：操作稍复杂，适用于没有交互的View。

----------
####View事件分发机制

#####MotionEvent:
　　在手指接触屏幕后所产生的一系列事件中，典型的事件类型有如下几种：

   >* ACTION_DOWN : 手指刚接触屏幕
   >* ACTION_MOVE : 手指在屏幕上移动
   >* ACTION_UP : 手指从屏幕松开一瞬间

　　正常情况下，一次手指触摸屏幕的行为会触发一系列点击时间，考虑如以下几种情况：

   >* 点击屏幕后离开松开，事件顺序为 DOWN→UP
   >* 点击屏幕滑一会再松开，事件顺序为 DOWN→MOVE→...→MOVE→UP

 　上述三种情况是典型的时间序列，同时通过 MotionEvent 对象我们可以得到点击事件发生的x和y坐标。为此，系统系统了两组方法：getX/getY和getRawX和getRawY。它们的区别其实很简单，getX/getY返回的是相对于当前View左上角的x和y坐标，而getRawX和getRawY返回的是相对于手机屏幕左上角的x和y坐标。

#####点击事件传递规则：
　　所谓的点击事件的事件分发，其实就是对MotionEvent事件的分发过程，即当一个MotionEvent产生了以后，系统需要把这个事件传递给一个具体的View，而这个传递的过程就是分发的过程。点击事件的分发过程由三个很重要的方法来共同完成：`dispatchTouchEvent`,`onInterceptTouchEvent`和`onTouchEvent`

>* `public boolean dispatchTouchEvent(MotionEvent event)`; 用来进行事件的分发。如果事件能够传递给当前View，那么此方法一定会被调用，返回结果受当前View的`onTouchEvent`和下级View的`dispatchTouchEvent`方法影响，表示是否消耗当前事件。
>* `public boolean onInterceptTouchEvent(MotionEvent event)`; 在上述方法内部调用，用来判断是否拦截某个事件，如果当前View拦截了某个事件，那么在同一个事件序列中，此方法不会被再次调用，返回结果表示是否拦截当前事件。
>* `public boolean onTouchEvent(MotionEvent event)` ; 在`dispatchTouchEvent`方法中调用，用来处理点击事件，返回结果标识是否消耗当前事件，如果不消耗，则在同一个事件序列中，当前view无法再次接收到事件。
