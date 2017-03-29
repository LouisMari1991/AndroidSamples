package com.sync.rxjavasamples.Create;

import android.support.annotation.NonNull;
import com.sync.rxjavasamples.RecyclerActivity;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

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
}
