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
　　到这里逻辑就很清晰了，PhoneWindow 将事件直接传递给了 DecorView。
```
private final class DecorView exrends FrameLayout implements RootViewSur-faceTaker

// This is the top-level view of the window, containing the window decor.
private DecorView mDecor;

@Override
public final View getDecorView(){
	if (mDecor == null){
		installDecor();
	}
	return mDecor;
}
```
　　我们知道，通过 `((ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content)).getChilldAt(0)` 这种方式就可以获取 Activity 所设置的 View， 这个 `mDecor` 显然就是 `((ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content)).getChilldAt(0)` 返回的 View, 而我们通过 `setContentView` 设置的 View 就是它的一个子 View。 目前事件传递到了 DevorView 这里，由于 DecorView 继承自 FrameLayout 且是父 View， 所以最终事件会传递给 View。 从这里开始，事件已经传递到顶级 View le，即在 Activity 中通过 `setContentView` 所设置的 View，另外顶级 `View` 也叫根 View，顶级 View 一般来说都是 ViewGroup。

#####顶层View对点击事件分发过程
　　点击事件达到顶层 View (一般是一个 ViewGroup)以后，会调用 ViewGroup 的 `dispatchTouchEvent` 方法，如果等级 ViewGroup 拦截事件即 `onInterceptTouchEvent` 返回 true,则事件由 ViewGroup 处理，这时如果 ViewGroup 的 `mOnTouchListenter` 被设置，则 `onTouch` 会被调用，否则 `onTcouchEvent` 会被调用。也就是说，如果都提供的话，`onTouch` 会屏蔽掉`onTouchEvent`。 在 `onTouchEvent` 中，如果设置了 `mOnCLickListener`，则 `onClick` 会被调用。 如果顶级 ViewGroup 不拦截事件，则事件会传递到它所在的点击事件链上的子 View，这时子 View 的 `dispatchTouchEvent` 会被调用。到此为止，事件已经从顶层 View 传递给了下一层 View， 接下来的传递过程和顶级 View 是一致的，如此循环，完成整个事件的分发。
 　　首先看 ViewGroup 对点击事件的分发过程。其主要实现在 ViewGroup 的 `dispatchTouchEvent` 方法中，这个方法比较长，这里分段说明。先看下面一段，很显然，它描述的是当前 View 是否拦截点击事件的逻辑。<br/>
```
// Check for interception
final boolean intercepted;
if (actoinMasked == MotionEvent.ACTION_DOWN || mFirstTouchTarget != null) {
	final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
	if (!disallowIntercept){
		intercepted = onInterceptTouchEvent(ev);
		ev.setAction(action); // restore action in case it was changed
	} else {
		intercepted = false;
	}
} else {
	// There are no touch targets and this action is not a initial down
	// so this view group continues to intercept touches.
	intercepted = true;
}
```
　　从上面的代码可以看出，ViewGroup 在下面两种情况下会判断是否要拦截当前事件：<br/>
　　事件类型为 `ACTION_DOWN` 或者 `mFirstTouchTarget != null `。 从后面的代码逻辑可以看出，当事件由 ViewGroup 的子元素成功处理时， `mFirstTouchTarget` 就会被赋值并指向子元素，换种方式说，当 ViewGroup 不拦截事件并将事件交由子元素处理时 `mFirstTouchTarget != null`。 反过来，一旦事件由当前 ViewGroup 拦截时， `mFirstTouchTarget != null` 就不成立。那么当 `ACTION_MOVE` 和 `ACTION_UP` 事件到来时，由于 `(actoinMasked == MotionEvent.ACTION_DOWN || mFirstTouchTarget != null)` 这个条件为 `false`，将导致 ViewGroup 的 `onInterceptTouchEvent` 不会再被调用，并且同一序列的其他事件都会默认交给它处理。

 　　当然，这里有一种特殊情况,那就是 `FLAG_DISALLOW_INTERCEPT` 标记位，这个标记位是通过 `requestDisallowInterceptTouchEvent` 方法来设置的，一般用于子 View 中。 `FLAG_DISALLOW_INTERCEPT` 一旦设置后， ViewGroup 将无法拦截除了 `ACTION_DOWN` 以外的其他点击事件。这是因为 ViewGroup 在分发事件时，如果是 `ACTION_DOWN` 就会重置 `FLAG_DISALLOW_INTERCEPT` 这个标记位，将导致 View 中设置的这个标记位无效。因此,当面对 `ACTION_DOWN` 事件时， ViewGroup 总会调用自己的 `onInterceptTouchEvent` 方法来询问自己是否要拦截时间，这一点从源码中也可以看出来。<br/>
　　在下面的代码中， ViewGroup 会在 `ACTION_DOWN` 事件到来时重置状态的操作，而在 `resetTouchState` 方法会对 `FLAG_DISALLOW_INTERCEPT` 进行重置，因此子 View 调用 `request-DisallowInterceptTouchEvent` 方法并不能影响 ViewGroup 对 `ACTON_DOWN` 事件的处理。
```
// Handle an initial down.
if (actionMasked == MotionEvent.ACTION_DOWN) {
	// Throw away all previous state when starting a new touch gesture.
	// The framework may have deopped the up or cancel event for the preious gesture.
	// due to an app switch, ANR, or some state other state change.
	cancelAndClearTouchTargets(ev);
	restTouchState();
}
``` 
　　从上面的源码分析，我们可以得出结论:当 ViewGroup 决定拦截事件后，那么后续的点击事件将会默认交给它处理并且不会调用它的 `onInterceptTouchEvent` 方法，这证实了 View 事件传递第三条结论。

　　总结起来有两点:<br/>
　　1. `onInterceptTouchEvent` 不是每次事件都会被调用的，如果我们小想要提前处理所有的点击事件，要选择 `dispatchTouchEvent` 。
　　　　2. `FLAG_DISALLOW_INTERCEPT` 标记位可以用来处理滑动冲突。

　　接着再看当 `ViewGroup` 不拦截事件的时候，事件会向下分发交由它的子 View 进行处理，源码如下：<br/>


```
	final View[] children = mChildren;
	for (int i = childrenCount -1; i >= 0; i--) {
		final int childIndex = customOrder ? getChildDrawingOrder(childrenCount, i) : i;
		final View child = (preorderedList == null) ? children[childIndex] : preorderedList.get(childIndex);
		if (!canViewReceivePointerEvents(child) || !isTransformedTouchPointInView(x, y, child, null)){
		continue;
	}

	newTouchTarget = getTouchTarget(child);
	if (newTouchTarget != null) {
		// Child is already reveiving touch within its bounds.
		// Give it the new pointer in addition to the ones it is handling. new TouchTarget.pointerIdBit != idBitsToAssign;
		break;
	}

	resetCancelNextUpFlag(child);
	if (dispatchTransformedTouchEvent(ev, false, child, idBitsToassign)) {
		// Child wants to receive touch within its bounds.
		mLastTouchDownTime = ev.getDownTime();
		if (preorderedList != null) {
			// childIndex points into presorted list, find original index
			for (int j = 0; j < childrenCount; j++) {
				if (children[childIndex] == mChildren[j] {
					mLastTouchDownIndex = j;
					break;
				}
			}
		} else {
			mLastTouchDownIndex = childIndex;
		}
		mLastTouchDownX = ev.getX();
		mLastTouchDownY = ev.getY();
		newTouchTarget = addTouchTarget(child, idBitsToAssign);
		alreadyDispatchedToNewTouchTarget = true;
		break;	
	}
}
```

 　　上面这段代码逻辑也很清晰，首先遍历 `ViewGroup` 的所有子元素，然后判断子元素是否能够接受到点击事件。是否能够接收点击事件主要由两点来衡量：子元素是否在播放动画和点击事件的坐标是否落在子元素的区域内。 如果某个子元素满足这两个条件，那么事件就会传递给它处理。可以看到，`dispatchTransformedTouchEvent` 实际上调用的是子元素的 `dispatchTouchEvent` 方法，在它的内部有如下一段内容，而在上面的代码中 child 传递的不是 `null`，因此他会直接调用子元素的 `dispatchTouchEvent` 方法，这样事件就交由给子元素处理了，从而完成一轮事件分发。


```

	if (child == null) {
		handled = super.dispatchTouchEvent(event);
	} else {
		handled = child.dispatchTouchEvent(event);
	}
```
<br/>　　如果子元素的 `dispatchTouchevent` 返回 `true` ，这时我们暂时不用考虑事件在子元素内部是怎么分发的，那么 `mFirstTouchEvent` 就会被赋值同时跳出 `for` 循环，如下所示:

```

	newTouchTarget = addTouchTargt(child, idBitsToAssign);
	alreadyDispatchToNewTouchTarget = true;
	break;

```
<br/>　　这几行代码完成了 `mFirstTouchTarget` 的复制并终止对子元素的遍历。如果子元素的 `dispatchTouchEvent` 返回 `false` ， `ViewGroup` 就会把事件分发给下一个子元素(如果还有下一个子元素的话)。<br/>

　　其实 `mFirstTouchTarget` 真正的赋值过程是在 `addTouchTarget` 内部完成的，从下面的 `addTouchTarget` 方法的内部结构可以看出， `mFirstTouchTarget` 其实是一种单链表结构。 `mFirstTouchTarget` 是否被赋值，将直接影响到 ViewGroup 对事件的拦截策略，如果 `mFirstTarget` 为 `null` , 那么 ViewGroup 就默认拦截接下来统一序列中所有的点击事件。

```

	private TouchTarget addTouchTarget(View child, int pointerIdBits) {
		TouchTarget target = TouchTarget.obtain(child, pointerIdBits);
		target.next = mFirstaTouchTarget;
		return target;
	}

```

　　如果子便利所有的子元素后事件没有被合适地处理，这里包含两种情况：第一种是 ViewGroup 没有子元素；第二种是子元素处理了点击事件，但是在 `dispatchTouchEvent` 中返回了 `false`, 这一般是因为子元素在 `onTouchEvent` 中返回了 `false` 。 在这两种情况下， ViewGroup 会自己处理点击事件， 这里就证实了 View 事件传递第四条结论。　代码如下所示：
		
```

	// Dispatch to touch targets.
	if (mFirstTouchTarget == null) {
		// No touch targets so treat this as an ordinary view.
		handled = dispatchTransformedTouchEvent(ev, canceled, null, TouchTarget.ALL_POINTER_IDS);
	}

```

　　注意上面这段代码，这里的第三个参数 child 为 `null`, 从前面的分析可以知道，它会调用 `super.dispatchTouchEvent(event)` ， 很显然，这里就转到了 `View` 的 `dispatchTouchEvent` 方法，即点击事件开始交由 `View` 来处理。

#####View对点击事件处理过程


----------
###View的滑动冲突
解决滑动冲突的两种方式

#####内部拦截法

#####外部拦截法