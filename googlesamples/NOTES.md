###Android 5.0Activity过渡动画

Activity Transition：
提供了三种Transition类型：
进入：一个进入的过渡(动画)决定activity中的所有的视图怎么进入屏幕。
退出：一个退出的过渡(动画)决定一个activity中的所有视图怎么退出屏幕。
共享元素：一个共享元素过渡(动画)决定两个activities之间的过渡，怎么共享(它们)的视图。

```
<span style="font-size:18px;color:#ff6666;">支持这些进入和退出的过渡动画:</span>
<span style="font-size:18px;">explode(分解) –进或出地移动视图，从屏幕中间
slide(滑动) -进或出地移动视图，从屏幕边缘
fade(淡出) –通过改变屏幕上视图的不透明度达到添加或者移除视图(的效果)</span>
```

<span style="font-size:18px;color:#ff6666;">在以上动画基础上还可以添加还支持共享元素过渡：(以上效果的共享元素效果基于分解动画基础上进行)</span>
它的作用就是共享两个acitivity种共同的元素，在Android 5.0下支持如下效果：
```
changeBounds -  改变目标视图的布局边界
changeClipBounds - 裁剪目标视图边界
changeTransform - 改变目标视图的缩放比例和旋转角度
changeImageTransform - 改变目标图片的大小和缩放比例
```

使用步骤：
1.设置动画（两种方式）：


1.1xml设置
当你定义继承了material主题样式时，使用android:windowContentTransitions属性启用窗口的内容转换(效果)。你还可以指定进入、退出、和共享元素的转换：

定义transition动画：
```
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
    <explode/>
    <changeBounds/>
    <changeTransform/>
    <changeClipBounds/>
    <changeImageTransform/>
</transitionSet>
```

代码设置:
```
Window.setEnterTransition()：设置进入动画
Window.setExitTransition()：设置退出效果
Window.setSharedElementEnterTransition()：设置共享元素的进入动画
Window.setSharedElementExitTransition()：设置共享元素的退出动画
```