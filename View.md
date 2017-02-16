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

它们之间的关系可以用如下伪代码表示：

```
public boolean dispatchTouchEvent(MotionEvent ev){
	boolean consume = false;
	if(onInterceptTouchEvent(ev){
		consume = onTouchEvent(ev);
	} else {
		consume = clild.dispatchTouchEvent(ev);
	}
	return consume;
}
```

当一个点击事件产生后，它的传递过程遵循如下顺序：Activity→Window→View。如果一个View的`onTouchEvent`返回false，那么它的父容器的`onTouchEvent`将会被调用，依次类推。如果所有的元素都不处理这个事件，那么这个事件将会最终传递给Activity处理，即Activity的`onTouchEvent`方法会被调用。

View事件传递结论：

1. 同一个事件序列是从手指接触屏幕那一刻起，到手指离开屏幕的那一刻结束，在这个过程中所产生的一系列事件，这个事件序列以`down`事件开始，中间含有数量不定的`move`事件，最终以`up`事件结束。
2. 正常情况下，一个事件序列只能被一个View拦截且消耗。因为一旦一个元素拦截了此事件，那么同一个事件序列内的所有事件都会直接交给它处理，因此同一个事件序列中的事件不能分别由两个View同时处理，但是通过特殊手段可以做到，比如一个View将本该自己处理的事件通过`onTouchEvent`强行传递给其他View处理。
3. 某个View一旦决定拦截，那么同一个事件序列内的所有事物都会直接交给它处理(如果事件序列能够传递给它的话)，并且它的`onInterceptTouchEvent`不会再被调用。也就是说当一个View决定拦截一个事件后，那么系统就会把同一个事件序列内的其他方法都交给它来处理，因此就不用再调用这个View的`onInterecptEvent`去询问它是否要拦截了。
4. 某个View一旦开始处理事件，那么它不消耗`ACTION_DOWN`事件(`onTouchEvent`返回了false),那么同一事件序列中的其他事件都不会再交给它来处理，并且事件将重新交给它的父元素去处理，即父元素的`onTouchEvent`会被调用。意思就是事件一旦交给一个View处理，那么它必须消耗掉，否则同一时间序列中剩下的事件就不会再交给它来处理了。
5. 如果View不消耗除`ACTITON_DOWN`以外的其他事件，那么这个点击事件会消失，此时父元素的ouTouchEvent并不会被调用，并且当前View可以持续收到后续的事件，最终这些消失的点击事件会传给Activity处理。
6. ViewGroup默认不拦截任何事件。Android源码中ViewGroup的`onInterceptEvent`方法默认返回false。
7. View没有`onInterceptTouchEvent`方法，一旦有点击事件传递给它，那么它的`onTouchEvent`方法就会被调用。
8. View的`onTouchEvent`默认都会消耗事件(返回true),除非它是不可点击的(`clickable`和`longClickble`同时为false)。View的`longClickble`属性默认都为false,`clickble`属性要分情况，不如Button的`clickble`属性默认为true,而TextView的``clickble`属性默认为false。
9. View的`enable`属性不影响`onTouchEvent`的默认返回值，哪怕一个View是`disable`状态的，只要它的`clickble`或者`longClickble`有一个为true，那么它的`onTouchEvent`就返回true。
10. `onClick`会发生的前提是当期View是可点击的，并且它收到了`down`和`up`的事件。
11. 事件传递的过程是由外向内的，即事件总是先传递给父元素，然后由父元素分发给子View，通过`requestDisallowInterceptTouchEvent`方法可以在子元素中干预父元素的事件分发过程，但是`ACTION_DOWN`事件除外。

----------
###事件分发源码解析

#####Activity对点击事件分发过程
　　点击事件用 `MotionEvent` 来表示，但一个点击操作发生时，事件最先传递给当前Activity，由Activity的 `dispatchTouchEvent` 来进行事件派发，具体的工作是由Activity内部的Window来完成的。Window会将事件传递给`decor view`，`decor view`一般就是当前界面的底层容器(即 `setContent()` 所设置的 View 的父容器),通过 `Activity.getWindow.getDecorView()` 可以获得。

源码： Activity#dispatchTouchEvent
```

public boolean dispatchTouchEvent(MotionEvent event){
	if(event.getAction == MotionEvent.ACTION_DOWN){
		onUserInteraction();
	}
	if(getWindow().superDispatchTouchEvent(event){
		return true;
	}
	return onTouchEvent(event);
}
```
　　首先事件开始交给Activity所附属的 Window 进行分发，如果返回 true ，整个事件循环就结束了，返回 false 意味者事件没人处理，所有 View 的 `onTouchEvent` 都返回了 false, 那么Activity 的 `onTouchEvent` 就会被调用。

　　Window 是如何将事件传递给 ViewGroup 的： Window 是一个抽象类，window的 `superDispatchTouchEvent` 方法也是一个抽奖方法。 Winow 的唯一实现是 `PhoneWinw`，所以我们看 `PhoneWindow` 是如何处理点击事件的：

源码：PhoneWindow#superDispatchTouchEvent<br>
```
@Override
public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
        return mDecor.superDispatchKeyShortcutEvent(event);
}
```

#####顶层View对点击事件分发过程


#####View对点击事件处理


----------
###View的滑动冲突
解决滑动冲突的两种方式

#####内部拦截法

#####外部拦截法