项目记录：<br/>
1. android5.0以上系统不能获取其他应用当前Activity，在5.0以上系统使用WatchingAccessibilityService，在5.0一下使用WatchingService解决兼容问题。<br/>
2. 系统自带的Switch控件不兼容v14以下版本，在layout文件中的布局使用CheckBox,在layout-v14文件中的布局使用Switch。解决兼容问题。<br/>
3. xml文件中使用boolass文件和<bools/>标签保存boolean值。values和values-21中的bools的标签一致，但是值不同，处理版本兼容。