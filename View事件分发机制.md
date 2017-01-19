###View事件分发机制

#####MotionEvent:
　　在手指接触屏幕后所产生的一系列事件中，典型的事件类型有如下几种：

   >* ACTION_DOWN : 手指刚接触屏幕
   >* ACTION_MOVE : 手指在屏幕上移动
   >* ACTION_UP : 手指从屏幕松开一瞬间

　　正常情况下，一次手指触摸屏幕的行为会触发一系列点击时间，考虑如以下几种情况：

   >* 点击屏幕后离开松开，事件顺序为 DOWN→UP
   >* 点击屏幕滑一会再松开，事件顺序为 DOWN→MOVE→...→MOVE→UP

 　上述三种情况是典型的时间序列，同时通过 MotionEvent 对象我们可以得到点击事件发生的x和y坐标。为此，系统系统了两组方法：getX/getY和getRawX和getRawY。它们的区别其实很简单，getX/getY返回的是相对于当前View左上角的x和y坐标，而getRawX和getRawY返回的是相对于手机屏幕左上角的x和y坐标。
