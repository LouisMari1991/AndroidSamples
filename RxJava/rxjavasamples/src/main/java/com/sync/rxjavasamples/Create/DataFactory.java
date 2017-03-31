package com.sync.rxjavasamples.Create;

import com.sync.rxjavasamples.Transform.Course;
import com.sync.rxjavasamples.Transform.Student;
import java.util.ArrayList;

/**
 * Author：SYNC on 2017/3/31 0031 20:17
 * Contact：289168296@qq.com
 */
public class DataFactory {

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
