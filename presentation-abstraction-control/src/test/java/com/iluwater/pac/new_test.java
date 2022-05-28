package com.iluwater.pac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class new_test {
  @Test
  //CS304 (manually written) Issue link: https://github.com/iluwatar/java-design-patterns/issues/304
  public void test1() {
    StudentStatusControl studentStatusControl = new StudentStatusControl();
    studentStatusControl.getStudentCourseControl().addCourseGrade("Math", 90.0f);
    studentStatusControl.getStudentCourseControl().addCourseGrade("Physics", 80.0f);
    assertEquals(studentStatusControl.getStudentAverageGradeControl().getAvg(), 85.0f);
  }

  @Test
  //CS304 (manually written) Issue link: https://github.com/iluwatar/java-design-patterns/issues/304
  public void test2() {
    StudentStatusControl studentStatusControl = new StudentStatusControl();
    studentStatusControl.getStudentCourseControl().addCourseGrade("Math", 90.0f);
    studentStatusControl.getStudentCourseControl().addCourseGrade("Physics", 70.0f);
    studentStatusControl.getStudentCourseControl().changeCourseGrade("Physics", 80.0f);
    studentStatusControl.getStudentAverageGradeControl().updatePreAndAbstraction(studentStatusControl.getStudentCourseControl().getStudentAbstraction());
    assertEquals(studentStatusControl.getStudentAverageGradeControl().getAvg(), 85.0f);
  }

}
