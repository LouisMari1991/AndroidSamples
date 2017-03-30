## ViewRoot和DecorView

　　ViewRoot 对应于 ViewRootImpl 类，它是连接 `WindowManager` 和 `DecorView` 的纽带， `View` 的三大流程均是通过 ViewRoot来完成的。 在 `ActivityThread` 中来完成的。 在 `ActivityThread` 中， 当 `Activity` 对象被创建完毕后，会将 `DecorView` 添加到 `Window` 中，同时会创建 `ViewRootImpl` 对象，并将 `ViewRootImpl` 对象和 `DecorView` 建立关联，这个过程可参看如下源码。


```java
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

### 理解 MeasureSpec

　　为了更好地理解 `View` 的测量过程，我们还需要理解 `MeasureSpec`,通过源码可以发现， `MeasureSpec` 参与了 `View` 的 `mesure` 过程, `MeasureSpec` 很大程度上决定了一个 `View` 的尺寸规格，之所以这么说是因为这个过程还受父容器的影响，因为父容器影响 `View` 的 `MeasureSpec` 的创建过程。 在测量过程中，系统将 `View` 的 `LayoutParams` 根据父容器所施加的规则转换成对应的 `MeasureSpec` ，然后再根据这个　`MeasureSpec` 来测量 `View` 的宽/高。

#### MeasureSpec

　　`MeasureSpec` 代表一个32位的 int 值，高2位代表 `SpecMode`, 低30位代表 `SpecSize` , `SpecMode` 是指测量模式， 而 `SpecSize` 是指某种测量模式下的规格大小。 下面先看一下 `MeasureSpec` 内部的一些常量的定义，通过下面的代码，应该不难理解 `MeasureSpec` 的工作原理：

```java
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
>* EXACTLY : 父容器已经检测出 `View` 所需要的精确大小，这个时候 `View` 的最终大小就是 `SpecSize` 所指定的值，它对应于 `LayoutParams` 中的 `match_parent` 和 具体的数值这两种模式。
>* AT_MOST : 父容器指定了一个可用大小即 `SpecSize` , `View` 的大小不能大于这个值，具体是什么值要看不同 `View` 的具体实现，它对应于 `LayoutParams` 中的 `warp_content` 。

<br/>

#### MeasureSpec 和 LayoutParams 的对应关系

　　上面提到，系统内部是通过 `MesureSpec` 来进行 `View` 的测量，但是正常情况下，我们使用 `View` 指定 `MesureSpec` ，尽管如此，但是我们可以给 `View` 设置 `LayoutParams` 。 在 `View` 测量的时候，系统会将 `LayoutParams` 在父容器的约束下转换成对应的 `MeasureSpec` , 然后再根据这个 `MeasureSpec` 来确定 `View` 测量后的宽/高。需要注意的是， `MeasureSpec` 不是唯一由 `LayoutParams` 决定的，`LayoutParams` 需要和父容器一起才能决定 `View` 的 `MeasureSpec` , 从而进一步决定 `View` 的宽/高。 另外，对于顶级 `View`(即 `DecorView`) 和普通 `View` 来说， `MeasureSpec` 的转换过程略有不同。对于 `DecorView` ， 其 `MeasureSpec` 由窗口尺寸和其自身的 `LayoutParams` 来共同确定；对于普通 `View` ，其 `MeasureSpec` 由父容器的 `MeasureSpec` 和自身的 `LayoutParams` 来共同决定， `MeasureSpec` 一旦确定后， `onMesure` 中就可以确定 `View` 的测量宽/高。

　　对于 `DecorView` 来说，在 `ViewRootImpl` 中的 `measureHierarchy` 方法中有如下一段代码，它展示了 `DecorView` 的 `MeasureSpec` 的创建过程，其中 `desiredWindowWidth` 和 `desiredWindowHeight` 是屏幕的尺寸：
```java
childWidthMeasureSpec = getRootMeasureSpec(desiredWindowWidtd, lp.width);
childHeightMeasureSpec = getRootMeasureSpec(desiredWindowHeight, lp.height);
```
接着再看下 `getRootMeasureSpec` 方法的实现：
```java
private static int getRootMeasureSpec(int windowSize, int rootDimension){
  int measureSpec;
  switch (rootDimension) {
    case ViewGroup.LayoutParams.MATCH_PARENT:
    // Window can't resize. Force root view to be windowSize.
    measureSpec = MeasureSpec.makeMeasureSpec(windowSize, MeasureSpec.EXACTLY);
    break;
    case ViewGroup.LayoutParams.WRAP_CONTENT:
    // Window can resize. Set max size for root view.
    measureSpec = MeasureSpec.makeMeasureSpec(windowSize, MeasureSpec.AT_MOST);
    break;
    default:
    // Window wants to be an exact size. Force root view to be that size.
    measureSpec = MeasureSpec.makeMeasureSpec(rootDimension, MeasureSpec.EXACTLY);
    break;
  }
  return measureSpec;
}
```
　　通过上述代码， `DecorView` 的 `MeasureSpec` 产生过程就很明确了，具体来说遵守如下规则，根据它的 `LayoutParams` 中的宽/高的参数来划分。

>* LayoutParams.MATCH_PARENT : 精确模式，大小就是窗口的大小；
>* LayoutParams.WRAP_CONTENT : 最大模式，大小不定，但是不能超过窗口的大小；
>* 固定大小 (比如 100dp) : 精确模式，大小为 `LayoutParams` 指定的大小。

　　对于普通 `View` 来说，这里是指定我们布局中的 `View` , `View` 的 `measure` 过程由 `ViewGroup` 传递而来，先看一下 `ViewGroup` 的 `mesureChildWidthMargins` 方法 :

```java
protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUesd){
  final MarginLayoutParams = lp = (MarginLayoutParams) child.getLayoutParams();

  final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, mPaddingLeft + mPaddindRight + lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
  final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin + heightUesd, lp.height);
  child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
}
```
　　上述方法会对子元素进行 `measure` ，在调用子元素的 `mesure` 方法之前会先通过 `getChildMeasureSpec` 方法来得到子元素的 `MeasureSpec` 。从代码来看，很显然，子元素的 `MeasureSpec` 的创建与父容器的 `MeasureSpec` 和子元素本身的 `LayoutParams` 有关， 此外还和 `View` 的 `margin` 及 `padding` 有关，具体情况可以看一下 ViewGroup 的 `getChildMeasureSpec` 方法，如下所示：
```java
public static int getChildMeasureSpec(int spec, int padding, int childDimension) {
  int specMode = MeasureSpec.getMode(spec);
  int specSize = MeasureSpec.getSize(spec);

  int size = Math.max(0, specSize - padding);

  int resultSize = 0;
  int resultMode = 0;

  switch (specMode) {
    // Parent has imposed an exact size on us
    case MeasureSpec.EXACTLY:
      if (childDimension >= 0) {
        resultSize = childDimension;
        resultMode = MeasureSpec.EXACTLY;
      } else if (childDimension == LayoutParams.MATCH_PARENT) {
        // Child wants to be our size. So be it.
        resultSize = size;
        resultMode = MeasureSpec.EXACTLY;
      } else if (childDimension == LayoutParams.WRAP_CONTENT) {
        // Child wants to determine its own size. It can't be bigger than us.
        resultSize = size;
        resultMode = MeasureSpec.AT_MOST;
      }
      break;

    // Parent has imposed a maximum size on us  
    case MeasureSpec.AT_MOST:
      if (childDimension >= 0) {
        // Child wants a specific size... so be it
        resultSize = childDimension;
        resultMode = MeasureSpec.EXACTLY;
      } else if (childDimension == LayoutParams.MATCH_PARENT) {
        // Child wants to be our size, but our size is not fixed.
        // Constarin child to not be bigger than us.
        resultSize = size;
        resultMode = MeasureSpec.AT_MOST;
      } else if (childDimension == LayoutParams.WRAP_CONTENT) {
        // Child wants to determine its own size. It can't be bigger than us.
        resultSize = size;
        resultMode = MeasureSpec.AT_MOST;
      }
      break;

      // Parent asked to se how big we want to be
      case MeasureSpec.UNSPECIFIED:
      if (childDimension >= 0) {
        // Child wants a specific size.. let him have it
        resultSize = childDimension;
        resultMode = MeasureSpec.EXACTLY;
      } else if (childDimension == LayoutParams.MATCH_PARENT) {
        // Child wants to be our size... find out how big it should be
        resultSize = 0;
        resultMode = MeasureSpec.UNSPECIFIED;
      } else if (childDimension == LayoutParams.WRAP_CONTENT) {
        // Child wants to determine its own size... find out how big it should be
        resultSize = 0;
        resultMode = MesureSpec.UNSPECIFIED;
      }
      break;
  }
  return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
}
```
　　上述方法不难理解，它的主要作用是根据父容器的 `MeasureSpec` 同时结合 `View` 本身的 `LayoutParams` 来确定子元素的 `MeasureSpec` , 参数中的 `padding` 是指父容器中已占用的空间大小，因此子元素可用的大小为父容器的尺寸减去 `padding` , 具体代码如下所示：
```java
int specSize = MeasureSpec.getSize(spec);
int size = Math.max(0, specSize - padding);
```
　　`getChildMeasureSpec` 清楚展示了普通 `View` 的创建规则，为了更清晰的理解 `getChildMeasureSpec` 的逻辑，这里提供一个表，表中对 `getChildMeasureSpec` 的工作原理进行了树梳理。注意，表中的 `parentSize` 是指父容器中目前可使用的大小。

<img src="https://raw.githubusercontent.com/MariShunxiang/GitTrainning/master/viewwork/viewwork3.bmp" width="50%" height="50%" />

　　对于普通 `View` ，其 `MeasureSpec` 由父容器的 `MeasureSpec` 和自身的 `LayoutParams` 来决定，那么针对不同的父容器和 `View` 本身不同的 `LayoutParams` ， `View` 就可以有多种 `MeasureSpec` 。 这里简单说一下， 当 `View` 采用固定宽/高的时候，不管父容器的 `MeasureSpec` 是什么， `View` 的 `MeasureSpec` 都是精确模式并且其大小遵循 `LayoutParams` 中的大小。 当 `View` 的宽/高是 `match_parent` 时， 如果父容器的模式是精准模式， 那么 `View` 也是精准模式并且其大小是父容器的剩余空间；如果父容器是最大模式，那么 `View` 也是最大模式并且其大小不会超过父容器的剩余空间。 当 `View` 的宽/高是 `wrap_content` 时，不管父容器的模式是精准还是最大化， `View` 的模式总是最大化并且大小不能超过父容器的剩余空间。 对于 `UNSPECIFIED` 模式主要用于系统内部多次 `Measure` 的情形，一般来说，我们不需要关注此模式。

　　通过表4-1 可以看出，只要提供父容器的 `MeasureSpec` 和子元素的 `LayoutParams` ，就可以快速的确定子元素的 `MeasureSpec` 了，有了 `MeasureSpec` 就可以进一步的确定出子元素测量后的大小了。需要说明的是，表4-1 并非什么经验总结，它只是 `getChildMeasureSpec` 这个方法以表格的方式呈现出来而已。
