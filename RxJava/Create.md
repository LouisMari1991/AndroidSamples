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
    Observer<String> subscriber = createObserver();
    // 2.被观察者
    Observable<String> observable = createObservable();
    // 3.订阅
    observable.subscribe(subscriber);
  }
```

>上面代码将会依次调用：
           onNext("Hello");
           onNext("Hi");
           onNext("Aloha");
           onCompleted();或者程序出现异常掉用onError


>流式 API 设计使得这里看起来像是：被观察者 订阅了 观察者

>其中Subscriber是Observable的抽象类，在使用过程中，Observable 也总是先被转换成一个 Subscriber 再使用。
onStart(): Subscriber类中新增方法，在subscribe所在线程执行，用于一些准备工作，如果需要指定线程可以使用doOnSubscribe()方法
unsubscribe()：Subscriber类中新增方法，是Subscription接口中的方法，Subscriber实现它，用于取消订阅，可以防止内存泄漏
isUnsubscribed()：Subscription接口中的方法，用于判断订阅状态，一般在使用unsubscribe()时先判断一下

## Just、From

 just 和 from 操作符用来快捷创建事件队列。
 `just(T...)` :将传入的参数依次发送出来(一次将整个的数组发射出去)。
 `from(T[])/from(Iterable<? extends T>)` :将传入的数组或 Iterable 拆分成对象后，依次发送出来(发射T.length次)。

 - from 操作符可以转换 Future、Iterable和数组，对于Iterable和数组，产生的 Observable 会发射 Iterable 或数组的每一项数据.
 对于 Future,它会发射 Future.get() 方法返回的单个数据

 - 如果你传递 null 给 Just,它会返回一个发射 null 值的 Observable,如果需要空 Observable 你应该使用 Empty 操作符.

 // 1.观察者
 ```java
 Observable<String> subscriber = createObserver();
 ```

 // 2.被观察者
 ```java
 // just
 Observable observable = Observable.just("just", "test", "just");
 ```


 ```java
 //from
 String[] words = {"from", "test", "from"};
 Observable observable = Observable.from(words);
 ```

 // 3. 订阅
 ```java
 observable.subscribe(subscriber);
 ```

## Range
Range 操作符发射一个范围内的有序整数序列，你可以指定范围的起始和长度。它接受两个参数，
一个是范围的起始值，一个是范围数据的数目。
如果你将第二个参数设为0，将导致 Observable 不发射任何数据（如果设置为负数，会抛异常）。

```java
@NonNull private Observer<Integer> createIntegerObserver() {
  return new Subscriber<Integer>() {
    @Override public void onCompleted() {
      logger("Completed!");
    }

    @Override public void onError(Throwable e) {
      logger("Error!");
    }

    @Override public void onNext(Integer integer) {
      logger("Item: " + integer);
    }
  };
}
```

```java
public void range() {
  //1.观察者
  Observer<Integer> subscriber = createIntegerObserver();
  //2.被观察者
  Observable observable = Observable.range(10, 5);
  //3:订阅:
  observable.subscribe(subscriber);
}
```

>上述代码将会打印：10，11，12，13，14，Completed!

## Defer
Defer 操作符只有当有 Subscriber 来订阅的时候才会创建一个新的 Observable 对象，
每次订阅都会得到一个刚创建的最新的 Observable 对象，确保 Observable 对象里面的数据是最新的.
Defer 操作符会一致等待有观察者订阅它，然后它使用 Observable 工厂方法生成一个 Observable.
如下代码就会打印当前实时时间：

```java
public void defer() {
   Observable.defer(new Func0<Observable<String>>() {
     @Override public Observable<String> call() {
       return Observable.just(String.valueOf(System.currentTimeMillis()));
     }
   }).subscribe(createStringObserver());
 }
```

> 和 just不同的是，just可以将数字、字符串、数组、Iterate对象转为Observable对象发射出去，但值是创建的时候就不变了的。
