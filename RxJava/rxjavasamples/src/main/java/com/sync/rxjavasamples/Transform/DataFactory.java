package com.sync.rxjavasamples.Transform;

import java.util.ArrayList;

/**
 * Author：SYNC on 2017/3/31 0031 20:17
 * Contact：289168296@qq.com
 */
public class DataFactory {


  /**
   * 获取学生集合
   *
   * [
   *      1:{
   *          id:1
   *          name:学生1
   *          course:[学生1的课程1,学生1的课程2,学生1的课程3]
   *        }
   *
   *      2:{
   *          id:2
   *          name:学生2
   *          course:[学生2的课程1,学生2的课程2,学生2的课程3]
   *        }
   *
   *      3:{
   *          id:3
   *          name:学生3
   *          course:[学生3的课程1,学生3的课程2,学生3的课程3]
   *        }
   * ]
   * @return
   */
  public static ArrayList<Student> getData() {

    ArrayList<Student> students = new ArrayList<>();

    ArrayList<Course> courses = null;
    Student student = null;
    Course course = null;

    for (int i = 0; i < 3; i++) {

      courses = new ArrayList<>();

      student = new Student();
      student.id = i;
      student.name = new StringBuffer("学生").append((i + 1)).toString();

      for (int j = 0; j < 2; j++) {
        course = new Course();
        course.id = j;
        course.name = new StringBuffer(student.name).append("的课程").append((j + 1)).toString();
        courses.add(course);
      }
      student.courses = courses;
    }

    return students;
  }
}
