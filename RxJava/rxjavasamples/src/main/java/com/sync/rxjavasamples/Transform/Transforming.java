package com.sync.rxjavasamples.Transform;

import com.sync.rxjavasamples.Create.DataFactory;
import com.sync.rxjavasamples.RecyclerActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * Author：SYNC on 2017/3/31 0031 20:40
 * Contact：289168296@qq.com
 */
public class Transforming extends RecyclerActivity {

  ////////////////////////////////////////////
  ///////////////////变化map///////////////////
  ////////////////////////////////////////////

  public void mapString2HashCode() {
    Observable.just("hello, world").map(new Func1<String, Integer>() {
      @Override public Integer call(String s) {
        return s.hashCode();
      }
    }).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        logger(integer.toString());
      }
    });
  }

  public void cast() {
    Observable.just(1, 2, 3, 4, 5, 6).cast(Integer.class).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        logger(" next : " + integer);
      }
    });
  }

  ////////////////////////////////////////////
  ///////////////////flatMap//////////////////
  ////////////////////////////////////////////

  public void studentNameMap() {
    ArrayList<Student> students = DataFactory.getData();

    Observable.from(students).map(new Func1<Student, String>() {
      @Override public String call(Student student) {
        return student.name;
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
      @Override public void call(String s) {
        logger(" 学生的姓名 : " + s);
      }
    });
  }

  /**
   * 每个学生有多个课程，如需打印每个学生 所修课程，上面的代码则类似下面这样
   */
  public void courseMap() {

    ArrayList<Student> students = DataFactory.getData();

    Observable.from(students).subscribe(new Action1<Student>() {
      @Override public void call(Student student) {
        List<Course> courses = student.courses;
        for (Course course : courses) {
          logger(courses.toString());
        }
      }
    });
  }

  public void flatMap() {

    ArrayList<Student> students = DataFactory.getData();

    Observable.from(students).flatMap(new Func1<Student, Observable<Course>>() {
      @Override public Observable<Course> call(Student student) {
        return Observable.from(student.courses);
      }
    }).subscribe(new Action1<Course>() {
      @Override public void call(Course course) {
        logger(course.name);
      }
    });
  }

  ////////////////////////////////////////////
  ////////////////flatMapIterable/////////////
  ////////////////////////////////////////////

  public void flatMapIterable() {
    Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).flatMapIterable(integer -> {
      ArrayList<Integer> s = new ArrayList<>();
      for (int i = 0; i < integer; i++) {
        s.add(integer);
      }
      return s;
    }).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        logger(integer);
      }
    });
  }

  public void concatMap() {
    // concatMap操作符的运行结果
    Subscription subscribe = Observable.just(10, 20, 30).concatMap(new Func1<Integer, Observable<Integer>>() {
      @Override public Observable<Integer> call(Integer integer) {
        // 10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
        int delay = 200;
        if (integer > 10) {
          delay = 180;
        }
        return Observable.from(new Integer[] { integer, integer / 2 }).delay(delay, TimeUnit.MILLISECONDS);
      }
    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        logger("concatMap Next:" + integer);
      }
    });
    addSubscription(subscribe);
  }

  public void switchMap() {
    //switchMap操作符的运行结果
    Subscription subscribe = Observable.just(10, 20, 30).switchMap(new Func1<Integer, Observable<Integer>>() {
      @Override public Observable<Integer> call(Integer integer) {
        // 10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
        int delay = 200;
        if (integer > 10) {
          delay = 180;
        }
        return Observable.from(new Integer[] { integer, integer / 2 }).delay(delay, TimeUnit.MILLISECONDS);
      }
    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        logger("switchMap Next:" + integer);
      }
    });
    addSubscription(subscribe);
  }

  ////////////////////////////////////////////
  ////////////////////buffer//////////////////
  ////////////////////////////////////////////

  public void buffer() {
    Observable.just(1, 2, 3, 4, 5, 6).buffer(2, 3).subscribe(new Action1<List<Integer>>() {
      @Override public void call(List<Integer> integers) {
        logger(integers);
      }
    });
  }

  public void intervalBuffer() {
    Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
        .buffer(3, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Long>>() {
          @Override public void call(List<Long> longs) {
            logger(longs);
          }
        });
    addSubscription(subscribe);
  }

  public void groupBy() {
    Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).groupBy(new Func1<Integer, Integer>() {
      @Override public Integer call(Integer integer) {
        return integer % 2;
      }
    }).subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
      @Override public void call(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
        integerIntegerGroupedObservable.count().subscribe(new Action1<Integer>() {
          @Override public void call(Integer integer) {
            logger("key" + integerIntegerGroupedObservable.getKey() + " contains:" + integer + " numbers");
          }
        });
      }
    });
  }

  public void scan() {
    Observable.just(1, 2, 3, 4, 5)
        .scan(new Func2<Integer, Integer, Integer>() {
          @Override public Integer call(Integer sum, Integer item) {
            return sum + item;
          }
        }).subscribe(new Subscriber<Integer>() {
      @Override public void onCompleted() {
        System.out.println("Sequence complete.");
      }

      @Override public void onError(Throwable e) {
        System.err.println("Error: " + e.getMessage());
      }

      @Override public void onNext(Integer item) {
        System.out.println("Next: " + item);
      }
    });
  }

  public void windowCount() {
    Subscription subscribe = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
        .window(3)
        .subscribe(new Action1<Observable<Integer>>() {
          @Override public void call(Observable<Integer> integerObservable) {
            logger(" windowCount : " + integerObservable);
          }
        });
    addSubscription(subscribe);
  }

  public void windowTime() {
    Subscription subscribe = Observable.interval(1000, TimeUnit.MILLISECONDS)
        .window(3000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Observable<Long>>() {
          @Override public void call(Observable<Long> longObservable) {
            longObservable.subscribe(new Action1<Long>() {
              @Override public void call(Long aLong) {
                logger(" windowTime : " + aLong);
              }
            });
          }
        });
    addSubscription(subscribe);
  }

}





















