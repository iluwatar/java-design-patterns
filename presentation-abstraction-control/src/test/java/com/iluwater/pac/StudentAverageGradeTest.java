package com.iluwater.pac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentAverageGradeTest {
  @Test
  public void testUpdate() {
    StudentStatusControl studentStatusControl = new StudentStatusControl();
    StudentAverageGradeControl studentAverageGradeControl = studentStatusControl.getStudentAverageGradeControl();
    StudentCourseAbstraction studentCourseAbstraction = studentStatusControl.getStudentCourseControl().getStudentAbstraction();
    studentCourseAbstraction.addGrade("Math", 90.0f);
    studentAverageGradeControl.updatePreAndAbstraction(studentCourseAbstraction);
    assertEquals(studentAverageGradeControl.getAvg(), 90.0f);
    studentCourseAbstraction.addGrade("Physics", 80.0f);
    studentAverageGradeControl.updatePreAndAbstraction(studentCourseAbstraction);
    assertEquals(studentAverageGradeControl.getAvg(), 85.0f);
  }

}
