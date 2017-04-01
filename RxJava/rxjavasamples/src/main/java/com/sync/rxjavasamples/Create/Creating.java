package com.sync.rxjavasamples.Create;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import com.sync.rxjavasamples.R;
import com.sync.rxjavasamples.RecyclerActivity;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author：SYNC on 2017/3/29 0029 21:57
 * Contact：289168296@qq.com
 */
public class Creating extends RecyclerActivity {

  //设定查询目录
  String PATh    = "/mnt/sdcard/DCIM/Camera";
  File[] floders = new File[] { new File(PATh) };

  // 常规做法
  public void doNormal() {

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    executorService.execute(new Runnable() {
      @Override public void run() {
        for (File folder : floders) {
          File[] files = folder.listFiles();

          for (File file : files) {
            if (file.isFile() && file.getName().equals(".jpg")) {

              final String path = file.getAbsolutePath();

              runOnUiThread(new Runnable() {
                @Override public void run() {
                  logger(path);
                }
              });
            }
          }
        }
      }
    });
  }

  public void uselambda() {
    Subscription subscription = Observable.from(floders)
        .flatMap(file -> Observable.from(file.listFiles()))
        .filter(file -> file.isFile() && file.getName().equals(".jpg"))
        .map(File::getAbsolutePath)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(s -> logger(s));
    addSubscription(subscription);
  }

  public void nolambda() {
    Subscription subscription = Observable.from(floders).flatMap(new Func1<File, Observable<File>>() {
      @Override public Observable<File> call(File file) {
        return Observable.from(file.listFiles());
      }
    }).filter(new Func1<File, Boolean>() {
      @Override public Boolean call(File file) {
        return file.isFile() && file.getName().equals(".jpg");
      }
    }).map(new Func1<File, String>() {
      @Override public String call(File file) {
        return file.getAbsolutePath();
      }
    }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
      @Override public void call(String s) {
        logger(s);
      }
    });
    addSubscription(subscription);
  }

  @NonNull private Observable<String> createStringObservable() {
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

  @NonNull private Observer<String> createStringObserver() {
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

  // Creating Observable
  public void create() {
    // 1.观察者
    Observer<String> subscriber = createStringObserver();
    // 2.被观察者
    Observable<String> observable = createStringObservable();
    // 3.订阅
    observable.subscribe(subscriber);
  }

  public void just() {
    // 1.观察者
    Observer<String> subscriber = createStringObserver();
    // 2.被观察者
    Observable<String> observable = Observable.just("just1", "just2", "just3");
    // 3.订阅
    observable.subscribe(subscriber);
  }

  public void from() {
    // 1.观察者
    Observer<String> subscriber = createStringObserver();
    // 2.被观察者
    String[] words = { "from1", "test", "from2" };
    Observable<String> observable = Observable.from(words);
    // 3.订阅
    observable.subscribe(subscriber);
  }

  public void range() {
    //1.观察者
    Observer<Integer> subscriber = createIntegerObserver();
    //2.被观察者
    Observable observable = Observable.range(10, 5);
    //3:订阅:
    observable.subscribe(subscriber);
  }

  public void defer() {
    Observable.defer(new Func0<Observable<String>>() {
      @Override public Observable<String> call() {
        return Observable.just(String.valueOf(System.currentTimeMillis()));
      }
    }).subscribe(createStringObserver());
  }

  /**
   * 不完整定义的回调
   */
  public void action() {
    // 1.观察者
    Action1<String> onNextAction = new Action1<String>() {
      // onNext()
      @Override public void call(String s) {
        logger(s);
      }
    };
    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
      // onError
      @Override public void call(Throwable throwable) {
        logger(throwable.toString());
      }
    };
    Action0 onCompleteAction = new Action0() {
      // onCompleted()
      @Override public void call() {
        logger("onmpleted");
      }
    };

    // 2.被观察者
    Observable<String> observable = Observable.just("just1", "just2", "just3", "just4");

    // 3.订阅
    // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
    observable.subscribe(onNextAction, onErrorAction, onCompleteAction);
  }

  public void scheduler() {
    int drawableRes = R.mipmap.ic_launcher;
    Subscription subscription = Observable.create(new Observable.OnSubscribe<Drawable>() {
      @Override public void call(Subscriber<? super Drawable> subscriber) {
        logger("start");

        // 模拟耗时操作
        SystemClock.sleep(3000);

        Drawable drawable = getResources().getDrawable(drawableRes);
        subscriber.onNext(drawable);
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
        .doOnSubscribe(new Action0() {
          @Override public void call() {
            progressVisible();
          }
        }).observeOn(AndroidSchedulers.mainThread())  // 指定 Subscriber 的回调发生在主线程
        .doOnUnsubscribe(new Action0() { // 取消订阅时的监听
          @Override public void call() {
            progressGone();
          }
        }).subscribe(new Observer<Drawable>() {
          @Override public void onCompleted() {
            logger("onCompleted()!");
            progressGone();
          }

          @Override public void onError(Throwable e) {
            logger("Error!");
            progressGone();
          }

          @Override public void onNext(Drawable drawable) {
            logger(drawable);
            progressGone();
          }
        });
    addSubscription(subscription);
  }

  public void interval() {
    Subscription subscription = Observable.interval(1, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Long>() {
          @Override public void onCompleted() {
            logger("onCompleted");
          }

          @Override public void onError(Throwable e) {
            logger("onError" + e.getMessage());
          }

          @Override public void onNext(Long aLong) {
            logger("interval:" + aLong);
          }
        });
    addSubscription(subscription);
  }

  public void repeat() {
    Observable.just(1, 2, 3, 4, 5)
        .repeat(5)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Integer>() {
          @Override public void call(Integer integer) {
            logger(" repeat : " + integer);
          }
        });
  }

  public void timer() {
    Observable.timer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
      @Override public void call(Long aLong) {
        logger(" timer : " + aLong);
      }
    });
  }

  public void repeatWhen() {
    Subscription subscription =
        Observable.range(10, 5).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
          @Override public Observable<?> call(Observable<? extends Void> observable) {
            return Observable.timer(3, TimeUnit.SECONDS);
          }
        }).subscribe(new Action1<Integer>() {
          @Override public void call(Integer integer) {
            logger(" repeatWhen : " + integer);
          }
        });
    addSubscription(subscription);
  }

  public void repeatDelay() {
    Subscription subscription =
        Observable.range(10, 5).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
          @Override public Observable<?> call(Observable<? extends Void> observable) {
            return observable.delay(2, TimeUnit.SECONDS);
          }
        }).subscribe(new Action1<Integer>() {
          @Override public void call(Integer integer) {

          }
        });
    addSubscription(subscription);
  }
}





























