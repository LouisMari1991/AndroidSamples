### View 的工作流程
　　View 的工作流程只要是指 `measure` `layout` `draw` 三大流程，即测量、布局和绘制。其中 `measure` 确定 View 的测量宽/高， `layout` 确定 View 的最终宽/高和四个顶点的位置，而 `draw` 则将 View 绘制到屏幕上。

#### mesure 过程

　　`measure` 过程要分情况来看，如果只是一个原始的 View ，那么通过 `measure` 方法就完成了其测量过程，如果是一个 `ViewGroup` ，除了完成自己的测量过程外，还会遍历去调用所有子 View 的 `measure` 方法，各个子元素再递归去执行这个流程，下面针对这两种情况分别讨论。

##### 1. View 的 `measure` 过程
　　View 的 `measure` 过程由其 `measure` 方法来完成，`measure` 方法是一个 `final` 类型方法，这意味着子类不能重写此方法，在 View 的 `measure` 方法中会去调用 View 的 `onMesure` 方法，因此只需要看 `onMesure` 的实现即可， View 的 `onMesure` 方法如下所示:
```
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
  setMesuredDimension(getDefaultSize(getSuggestedMininumWidth(), widthMeasureSpec),
   getDefaultSize(getSuggestedMininumHeight(), heightMeasureSpec));
}
```
　　上述代码很简洁，但是简洁并不代表简单， `setMesuredDimension` 方法会设置 View 宽/高的测量值，因此我们只需要看 `getDefaultSize` 这个方法即可:
```
public static final getDefaultSize(int size, int measureSpec) {
  int result = size;
  int specMode = MeasureSpec.getMode(measureSpec);
  int specSize = MeasureSpec.getSize(measureSpec);

  switch (specMode) {
    case MeasureSpec.UNSPECIFIED:
      result = size;
      break;
    case MeasureSpec.EXACTLY:
    case MeasureSpec.AT_MOST:
      result = specSize;
      break;
  }
  return result;
}
```
　　可以看出 `getDefaultSize` 这个方法的逻辑很简单，对于我们来说，我们只需要看 `AT_MOST` 和 `EXACTLY` 这两种情况。简单的理解，其实 `getDefaultSize` 返回的大小就是 `measureSpec` 中的 `specSize` ,而这个 `specSize` 就是 View 测量后的大小，这里多次提到测量后的大小，是因为 View 最终的大小是在 `layout` 阶段确定的，所以这里必须要加以区分，但是几乎所有情况下 View 的测量大小和最终大小是相等的。

　　至于 `UNSPECIFIED` 这种情况，一般用于系统内部的测量过程，在这种情况下， View 的大小为 `getDefaultSize` 的第一个参数 size ，即宽/高分别为 `getSuggestedMininumHeight` 和 `getSuggestedMininumWidth` 这两个方法的返回值。看一下它们的源码：
```
protected int getSuggestedMininumWidth() {
  return (mBackground == null) ? mMinWidth : max(mMinWidth, mBackground.getMinimumWidth());
}

protected int getSuggestedMininumHeight() {
  return (mBackground == null) ? mMinHeight : max(mMinHeight, mBackground.getMinimumHeight());
}
```
　　这里只分析 `getSuggestedMininumWidth` 的实现， `getSuggestedMininumHeight` 和它的实现原理是一样的。 从 `getSuggestedMininumWidth` 的代码可以看出，如果 View 没有设置背景，那么 View 的宽度为 `mMinWidth` ,而 `mMinWidth` 对应于 `android:minWidth` 这个属性所指定的值，因此 View 的宽度即为 `android:minWidth` 属性所指定的值。这个属性如果不指定，那么 `mMinWidth` 的默认为 0，如果 `View` 指定了背景， 则 View 的宽度为 `max(mMinWidth, mBackground.getMinimumWidth())` 。 `mMinWidth` 的含义我们已经知道了，那么 `mBackground.getMinimumWidth()` 是什么呢？ 我们看一下 `Drawable` 的 `getMinimumWidth` 方法，如下所示：
```
public int getMinimumWidth() {
  final int intrinsicWidth = getIntrinsicWidth();
  return intrinsicWidth > 0 ? intrinsicWidth : 0;
}
```
　　可以看出， `getMinimumWidth` 返回的就是 `Drawable` 的原始宽度，前提是这个`Drawable` 有原始宽度，否则就返回0。 那么 `Drawable` 在什么情况下有原始宽度呢？ 这里先举个例子说明一下， `ShapeDrawable` 无原始宽/高，而 `BitmapDrawablr` 有原始宽高（图片的尺寸）。

　　这里再总结一下， `getSuggestedMininumWidth` 的逻辑；如果 View 没有设置背景，那么返回 `android:minWidth` 这个属性所指定的值，这个值可以为0； 如果 View 设置了背景，则返回 `android:minWidth` 和背景的最小宽度这两者中的最大值，`getSuggestedMininumWidth` 和 `getSuggestedMininumHeight` 的返回值就是 View 在 `UNSPECIFIED` 情况下的测量宽/高。

 　　从 `getDefaultSize` 方法实现来看， View 的宽/高由 `specSize` 来决定，所以我们可以得出如下结论：直接继承 View 的自定义控件需要重写 `onMesure` 方法并设置 `wrap_conent` 时的自身大小，否则在布局中使用 `wrap_conent` 就相当于使用 `match_parent` 。 因为根据 图4-1 和上述代码可知，如果 View 在布局中使用 `wrap_conent` ，那么它的 `specMode` 是 `AT_MOST` 模式，这种模式下，它的宽/高等于 `specSize` , 查看 图4-1 可知，这种情况下 `specSize` 是 `parentSize` ,而 `parentSize` 是父容器中目前可以使用的大小，也就是父容器当前剩余的空间大小。很显然， View 的宽/高就等于父容器当前剩余的空间大小，这种效果和在布局中使用 `match_parent` 完全一致。如何解决这个问题呢？也很简单，代码如下所示：
 ```
 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
   int widthSpecMoe = MeasureSpec.getMode(widthMeasureSpec);
   int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
   int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
   int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

   if (widthSpecMoe == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
     setMesuredDimension(mWidth, mHeight);
   } else if (widthSpecMoe == MeasureSpec.AT_MOST) {
     setMesuredDimension(mWidth, heightSpecSize);
   } else if (heightSpecMode == MeasureSpec.AT_MOST) {
     setMesuredDimension(widthSpecSize, mHeight);
   }
 }
 ```
　　上述代码中，我们只需要给 View 一个指定一个默认的内部宽/高(mWidth和mHeight)，并在 `wrap_conent` 时设置此宽/高即可。如果查看 `TextView`、 `ImageView` 等的源码就可以知道，针对 `wrap_conent` 情形，它们的 `onMesure` 方法均做了特殊处理。

##### 2. ViewGroup 的 `measure` 过程

　　对于 ViewGroup 来说，除了完成自己的 `mesure` 过程以外，还会遍历去调用所有子元素的 `measure` 方法，各个子元素再递归去执行这个过程，和 View 不同的是， ViewGroup 是一个抽象类，因此它没有重写 View `onMeasure` 方法，但是它提供了一个叫 `measureChildren` 的方法，如下所示：
```
protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
  final int size = mChildrenCount;
  final View[] children = mChildren;
  for (int i = 0; i < size; i ++) {
    final View child = children[i];
    if ((child.mViewFlags & VISIBILITY) != GONE) {
      measureChildren(child, widthMeasureSpec, heightMeasureSpec);
    }
  }
}
```
　　从上述代码来看， ViewGroup 在 `mesure` 时，会对每一个子元素进行 `mesure` ， `measureChild` 这个方法的实现也很好理解，如下所示：
```
protected void measureChild(View child, int parentWidthMeasureSpec, int  parentHeightMeasureSpec) {
  final LayoutParams lp = child.getLayoutParams();

  final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
    mPaddingLeft + mPaddingRight, lp.width);
  final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
    mPaddingTop + mPaddingBottom, lp.height);

  child.measure(childWidthMeasureSpec, childHeightMeasureSpec);    
}
```
　　很显然， `measureChild` 的思想是取出子元素的 `LayoutParams` ，然后再通过 `getChildMeasureSpec` 来创建子元素的 `MeasureSpec` ，接着将 `MeasureSpec` 直接传递给 View 的 `measure` 方法来进行测量。 `getChildMeasureSpec` 已经在上面进行了详细分析，通过表4-1 可以更清楚了解它的逻辑。

　　我们知道， ViewGroup 并没有定义其测量的具体过程，这是因为 ViewGroup 是一个抽象类，其测量过程的 `onMesure` 方法需要各个子类去具体实现， 比如 `LinearLayout`、`RelativeLayout` 等，为什么 ViewGroup 不像 View 一样对其 `onMesure` 方法做统一的实现呢？　那是因为不同的 ViewGroup 有不同的布局特性，这导致他们的测量细节各不相同，比如 `LinearLayout` 和 `RelativeLayout` 这两者的布局特性显然不同，因此 ViewGroup 无法做统一实现。 下面就通过 `LinearLayout` 的 `onMesure` 方法来分析 ViewGroup 的 `mesure` 过程。

　　首先来看 `LinearLayout` 的 `onMesure` 方法，如下所示：
```
protected void onMesure(int widthMeasureSpec, int heightMeasureSpec) {
  if (mRientation == VERTICAL) {
    measureVertical(widthMeasureSpec, heightMeasureSpec);
  } else {
    measureHorizontal(widthMeasureSpec, heightMeasureSpec);
  }
}
```

　　上述代码很简单，我们选择一个来看下，比如选择查看数值布局的 `LinearLayout` 的测量过程，即 `measureVertical` 方法， `measureVertical` 的源码比较长，下面只描述其大概逻辑，首先看一段代码：
```
// See how tall everyone is. Also remember max width.
for  (int i = 0; i < count; ++i) {
  finnal View child = getVertualChildAt(i);
  ...
  // Determine how big this child would like to be. If this or
  // previous children have qiven a weight, then we allow it to
  // use all avaiable space (and we will shrink things later
  // if needed).
  mesureChildBeforeLayout(
    child, i, widthMeasureSpec, 0, heightMeasureSpec,
    totalWeight == 0 ? mTotalLenght : 0);

  if (oldHeight != Integer.MIN_VALUE) {
    lp.height = oldHeight;
  }

  final int childHeight = child.getMeasureHeight();
  final int totalLenght = mTotalLength;
  mTotalLength = Math.max(totalLenght, totalLenght + childHeight + lp.topMargin +
    lp.bottomMargin + getNextLocationOffset(child));
}
```
　　从上面这段代码可以看出，系统会遍历子元素并对每个子元素执行 `mesureChildBeforeLayout` ，这个方法内部会调用子元素的 `mesure` 方法,这样各个子元素就开始依次进入 `mesure` 过程,并且系统会通过 `mTotalLenght` 这个变量来存储 `LinearLayout` 在竖直方向的初步高度。每测量一个子元素， `mTotalLenght` 就会增加，增加的部分主要包括子元素的高度以及子元素在竖直方向上的 `margin` 等。当子元素测量完毕后， `LinearLayout` 会测量自己的大小，源码如下所示：
```
  // Add in our padding
mTotalLenght += mPaddingTop + mPaddingBottom;
int heightSize = mTotalLenght;
// Check against out mininum height
heightSize = Math.max(heightSize, getSuggestedMininumHeight());
// Reconcile out calculated size with the heightMeasureSpec
int heightSizeAndState=resolveSizeAndState(heightSize, heightMeasureSpec, 0);
heightSize = heightSizeAndState & MEASURE_SIZE_MASK;
...
setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState), heightSizeAndState);
```
　　这里对上述代码进行说明，当子元素测量完毕后， `LinearLayout` 会根据子元素的情况来测量自己的大小。针对竖直的 `LinearLayout` 而言。它在水平方向的测量过程遵循 `View` 的测量过程，在竖直方向的测量过程则和 `View` 有所不同。具体来说是指，如果它的布局中高度采取的是 `match_parent` 或者具体的数值，那么它的测量过程和 `View` 一致，及高度为 `specSize` ;如果他的布局中采用的是 `wrap_conent` ，那么它的高度是所有的子元素所占用的高度总和，但是任然不能超过它的父容器的剩余空间，当然它的最终高度还需要考虑其在竖直方向的 `padding` ，这个过程可以进一步参看如下源码：
```
public static int resolveSizeAndState(int size, int mesureSpec, int childMeasuredState) {
  int result = size;
  int specMode = MeasureSpec.getMode(mesureSpec);
  int specSize = MeasureSpec.getSize(mesureSpec);
  switch (specMode) {
    case mesureSpec.UNSPECIFIED:
      result = size;
      break;
    case MeasureSpec.AT_MOST:
      if (specSize < size) {
        result = specSize | MEASURED_STATE_TOO_SMALL;
      } else {
        result = size;
      }
      break;
    case mesureSpec.EXACTLY:
      result = specSize;
      break;
  }
}
```
　　View 的 `measure` 过程是三大流程中最复杂的一个， `measure` 完成以后，通过 `getMeasuredWith/Height` 方法就可以正确地获取到 View 的测量宽/高。需要注意的是，在某些极端情况下，系统可能需要多次 `mesure` 才能确定最终的测量宽/高，这种情况下，在 `onMeasure` 方法中拿到的测量宽/高很可能不准确的。一个比较好的的习惯是在 `onLayout` 方法中去获取 View 的测量宽/高或者最终宽/高。
　　上面已经对 View 的 `measure` 过程进行了详细的分析，现在考虑一种情况，比如我们想在 `Activity` 已启动的时候就做一件任务，但是这个任务需要获取某个 View  的宽/高。读者可能会说，这很简单啊，在 `onCreate` 或者 `onResume` 里面去获取这个 View 的宽/高不就行了？读者可以自行试一下，实际上在 `onCreate`，`onStart`,`onResume` 中无法正确得到某个 View 的宽/高信息，这是因为 View 的 `mesure` 过程和 `Activity` 生命周期不是同步执行的，因此无法保证 `Activity` 执行了 `onCreate`,`onStart`,`onResume` 时某个 View 已经测量完毕了，如果 View 还没有测量完毕，那么获得的宽/高就是 0 。 有没有什么方法能解决这个问题呢？ 答案是有的，这里给出四种方法来解决这个问题：
   
