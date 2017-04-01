## 概述


RxJava是一个实现异步操作的库，采用链式掉用来实现响应式编程，使逻辑代码更加清晰。是ReactiveX的Java版本实现。

RxJava类似观察者模式，Observables (被观察者)和 Observers(Subscribers) (观察者)通过 subscribe(订阅)方法实现订阅关系
Observables 在需要的时候发出事件来通知 Observers(Subscribers).

类似Android中Button的点击事件的监听：
Button -> 被观察者、OnClickListener -> 观察者、setOnClickListener() -> 订阅，onClick() -> 事件

和观察者模式不同的是 ：

一个"热"的Observable可能一创建完就开始发射数据，因此所有后续订阅它的观察者可能从序列中间的某个位置开始接受数据（会丢失一些数据）。<br/>
一个"冷"的Observable会一直等待，直到有观察者订阅它才开始发射数据，因此这个观察者可以确保会收到整个数据序列。

## RxJava回调方法

RxJava中定义了三种回调方法：

 - onNext()：相当于 onClick() / onEvent()
 - onCompleted(): 事件队列完结，时间队列中没有 新的 onNext() 发出时触发
 - onError(): 事件队列异常，事件处理过程出异常时，onError() 会被触发，同时队列自动终止，不再有事件发出
 - onCompleted() 和 onError() 二者互斥，在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只会触发一个


## 基本实现


创建一个RxJava掉用实现需要三个步骤
   1. 创建观察者 Observer或者Subscriber
   2. 创建被观察者 Observable
   3. 订阅subscribe


 HelloWorld:

```java
@NonNull private Observer<String> createObserver() {
    return new Subscriber<String>() {
      @Override public void onCompleted() {
        logger(" onCompleted! ");
      }

      @Override public void onError(Throwable e) {
        logger("Error! , e : " + e.getMessage());
      }

      @Override public void onNext(String s) {
        logger(" Item : " + s);
      }
    };
  }
```

```java
@NonNull private Observable<String> createObservable() {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("Hello");
        subscriber.onNext("Hi");
        subscriber.onNext("Aloha");
        subscriber.onCompleted();
        subscriber.onError(new Throwable());
      }
    });
  }
```

>这里传入了一个 OnSubscribe 对象作为参数.
OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，
当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会被自动调用，
事件序列就会依照设定依次触发

>上面的定义就是：
观察者 Subscriber 将会被调用三次 onNext() 和一次 onCompleted()，其中 onError 和 onCompleted 互斥。
被观察者调用了观察者的回调方法，就实现了由被观察者向观察者的事件传递，即观察者模式。


```java
  // Creating Observable
  public void create() {
    // 1.观察者
    Observer<String> subscriber = createStringObserver();
    // 2.被观察者
    Observable<String> observable = createStringObservable();
    // 3.订阅
    observable.subscribe(subscriber);
  }
```
