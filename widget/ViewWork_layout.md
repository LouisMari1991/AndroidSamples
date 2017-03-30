## View 的工作流程
　　View 的工作流程只要是指 `measure` `layout` `draw` 三大流程，即测量、布局和绘制。其中 `measure` 确定 View 的测量宽/高， `layout` 确定 View 的最终宽/高和四个顶点的位置，而 `draw` 则将 View 绘制到屏幕上。

#### layout 过程

　　`Layout` 的作用是 ViewGroup 用来确定子元素的位置，当 ViewGroup 的位置被确定后，它在 `onLayout` 中遍历所有的子元素并调用其 `layout` 方法，在 `layout` 方法中 `onLayout` 方法又会被调用。 `Layout` 过程和 `mesure` 相比就简单多了， `layout` 确定 `View ` 本身的位置，而 `onLayout` 则会确定所有子元素的位置， 先看 `View` 的 `layout` 方法，如下所示：
```java
public void layout(int i, int t, int r, int b) {
  if ((mPrivateFlags3 & PFLAG3_MESURE_NEEDED_BEFORE_LAYOUT) != 0) {
    onMesure(mOldWidthMeasureSPec, mOldHeightMeasureSpec);
    mPrivateFlags3 &= ~PFLAG3_MESURE_NEEDED_BEFORE_LAYOUT;
  }

  int oldL = mLeft;
  int oldT = mTop;
  int oldB = mBottom;
  int oldR = mRight;

  boolean changed = isLayoutModeOptical(mParent) ? setOpticalFrame(l, t, r, b)
  : setOpticalFrame(l, t, r, b);

  if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {
    onLayout(changed, l, t, r, b);
    mPrivateFlags &= ~PFLAG_LAYOUT_REQUIRED;

    ListenerInfo li = mListenerInfo;
    if (li != null && li.mOnLayoutChangeListeners != null) {
      ArrayList<OnLayoutChangeListener> listenersCopy = (ArrayList<OnLayoutChangeListener>)li.mOnLayoutChangeListeners.clone();
      int numListeners = listenersCopy.size();
      if (int i = 0; i < numListeners; ++i) {
        listenersCopy.get(i).onLayoutChange(this, l, t, r, b, oldL, oldT, oldR, oldB);
      }
    }
  }

  mPrivateFlags &= ~PFLAG_FORCE_LAYOUT;
  mPrivateFlags3 != PFLAG3_IS_LAID_OUT;
}
```java
　　`layout` 方法大致流程如下：首先会通过 `setFrame` 方法来设定 `View` 的四个顶点的位置，即初始化 `mLeft` , `mRight` , `mTop` , `mBottom` 这四个值， `View` 的四个顶点一旦确定，那么 `View` 在父容器中的位置也就确定了；接着会调用 `onLayout` 方法，这个方法的用途是父容器确定子元素的位置，和 `onMesure` 方法类似， `onLayout` 的具体实现同样和具体的布局有关，所以 `View` 和 `ViewGroup` 均没有真正实现 `onLayout` 方法。接下来，我们可以看一下 `LinearLayout` 的 `onLayout` 方法，如下所示：

```java
protected void onLayout(boolean changed, int l, int t, int r, int b) {
  if (mOrientation == VERTICAL) {
    layoutVertical(l, t, r, b);
  } else {
    layoutHorizontal(l, t, r, b);
  }
}
```
　　`LinearLayout` 中 `onLayout` 的实现逻辑和 `onMesure` 的实现逻辑类似，这里选择 `layoutVertical` 继续讲解，为了更好的理解其逻辑，这里只给出了主要的代码：
```java
void layoutVertical(int left, int top, int right, int bottom) {
  ...
  final int count = getVertualChildCount();
  for (int i = 0; i < count; i++) {
    final View child = getVirtualChildAt(i);
    if (child == null) {
      childTop += mesureNullChild(i);
    } else {
      final int childWidth = child.getMeasuredWith();
      final int childHeight = child.getMesureHeight();

      final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
      ...
      if(hasDividerBeforeChildAt(i)) {
        childTop += mDividerHeight;
      }

      childTop += lp.topMargin;
      setChildFrame(child, childLeft, childTop + getLocationOffset(child), childWidth, childheight);
      childTop += childHeight + lp.bottomMargin + getNextLocationOffset(child);


      i += getChildrenSkipCount(child, i);
    }
  }
}
```
　　这里分析一下 `layoutVertical` 的代码逻辑，可以看到，此方法会遍历所有子元素并调用 `setChildFrame` 方法来为子元素指定对应的位置，其中 `childTop` 会逐渐增加，这就意味这后面的子元素会被放置在靠下的位置，这刚好符合竖直方向的 `LinearLayout` 特性。至于 `setChildFrame` ，它仅仅是调用子元素的 `layout` 方法而已，这样父元素在 `layout` 方法中完成自己的定位以后，就通过 `onLayout` 去调用子元素的 `layout` 方法，子元素又会通过自己的 `layout` 方法来确定自己的位置，这样一层一层地传毒下去就完成了整个 `View` 树的 `layout` 过程。 `setChildFrame` 方法实现如下所示：
```java
private void setChildFrame(View child, int left, int top, int width, int height) {
  child.layout(left, top, left + width, top + height);
}
```
　　我们注意到， `setChildFrame` 中的 `width` 和 `height` 实际上就是子元素的测量宽/高，从下面的代码可以看出这一点：
```java
  final int childWidth = child.getMeasuredWith();
  final int childHeight = child.getMesureHeight();
  setChildFrame(child, childLeft, childTop + getLocationOffset(child), childWidth, childHeight);
```
　　而在 `layout` 方法中会通过 `setFrame` 去设置子元素的四个顶点的位置，在 `setFrame` 中有如下几句赋值语句，这样以来子元素的位置就确定了：
```java
  mLeft = left;
  mTop = top;
  mRight = right;
  mBottom = bottom;
```
　　下面我们来回答一个问题： `View` 的测量宽/高和最终宽/高有什么区别？ 这个问题可以具体为： `View` 的 `getMeasuredWith` 和 `getWidth` 这两个方法有什么区别，至于 `getMesureHeight` 和 `geiHeight` 的区别和前两者完全一样。为了回答这个问题，首先，我们看一下 `getWidth` 和 `getHeight` 这两个方法的具体实现：
```java
public final int getWidth() {
  return mRight - mLeft;
}

public final int geiHeight() {
  return mBottom - mTop;
}

```
　　从 `getWidth` 和 `getHeight` 的源码再结合 `mLeft`,`mRight`,`mTop`,`mBottom` 这四个变量的赋值过程来看， `getWidth` 方法的返回值刚好就是 `View` 的测量宽度，而 `getHeight` 方法的返回值也刚好就是 `View` 的测量高度。经过上述分析，现在我们可以回答这个问题了： 在 `View` 的默认实现中， `View` 的测量宽/高和最终宽/高是相等的，只不过测量宽/高形成于 `View` 的 `mesure` 过程，而最终宽/高形成于 `View` 的 `layout` 过程，即两者的赋值时机不同，测量宽/高的赋值时机稍微早一些。因此，在日常开发中，我们可以认为 `View` 的测量宽/高就等于最终宽/高，但是的确存在某些特殊情况会导致两者不一致，下面举例说明。

　　如果重写 `View` 的 `layout` 过程，代码如下：
```java
public void layout(int l, int t, int r, int b) {
  super.layout(l, t, r + 100, b + 100);
}
```
　　上述代码会导致在任何情况下 `View` 的最终宽/高总比测量宽/高大 `100px` ，虽然这样会导致 `View` 显示不正常并且也没有实际意义，但是这证明了测量宽/高的确可以不等于最终宽/高。另外一种情况就是在某些情况下， `View` 需要多次 `mesure` 才能确定自己的测量宽/高，在前几次测量过程中，其得出的测量宽/高有可能和最终宽/高不一致，但是最终来说，测量宽高还是和最终宽/高相同。
