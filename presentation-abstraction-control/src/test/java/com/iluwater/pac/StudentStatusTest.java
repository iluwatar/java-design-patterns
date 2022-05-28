package com.iluwater.pac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentStatusTest {
  static StudentStatusControl studentStatusControl;
  static StudentCourseControl studentCourseControl;

  @Test
  public void testStatus() {
    studentStatusControl = new StudentStatusControl();
    studentCourseControl = studentStatusControl.getStudentCourseControl();
    studentCourseControl.addCourseGrade("Math", 90.0f);
    studentCourseControl.addCourseGrade("Physics", 20.0f);

    assertEquals(studentStatusControl.getStudentAverageGradeControl().getAvg(), 55.0f);
    assertEquals(studentStatusControl.getStatus(), false);
  }

  @Test
  public void testUpdateStatus() {
    studentStatusControl = new StudentStatusControl();
    studentCourseControl = studentStatusControl.getStudentCourseControl();
    studentCourseControl.addCourseGrade("Math", 90.0f);
    studentCourseControl.addCourseGrade("Physics", 20.0f);
    studentCourseControl.addCourseGrade("Geology", 90.0f);

    assertEquals(studentStatusControl.getStatus(), true);
  }
}
