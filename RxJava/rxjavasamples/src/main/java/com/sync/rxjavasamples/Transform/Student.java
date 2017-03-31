package com.sync.rxjavasamples.Transform;

import java.util.ArrayList;

/**
 * Description: 学生类
 * Author：SYNC on 2017/3/31 0031 20:09
 * Contact：289168296@qq.com
 */
public class Student {

  public int               id; // 学号
  public String            name; // 姓名
  public ArrayList<Course> courses;//学生选修的课程

  @Override public String toString() {
    return "Student{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", courses=" + courses +
        '}';
  }
}
