package com.sync.rxjavasamples.Transform;

/**
 * 课程类
 * Author：SYNC on 2017/3/31 0031 20:13
 * Contact：289168296@qq.com
 */
public class Course {

  public int    id;  // 课程编号
  public String name;// 课程名称

  @Override public String toString() {
    return "Course{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
