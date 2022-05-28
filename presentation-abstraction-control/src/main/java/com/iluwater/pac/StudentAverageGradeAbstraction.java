package com.iluwater.pac;

import lombok.Data;

/**
 * a class used to abstract student average grade.
 */

@Data
public class StudentAverageGradeAbstraction {
  private float avg = 0f;

  /**
   * calculate and update the average grade according to the given parameter.
   *
   * @param studentCourseAbstraction is an object that contains the data of all course grades.
   */
  public void updateAvg(StudentCourseAbstraction studentCourseAbstraction) {
    Object[] grades = studentCourseAbstraction.getGrades();
    float sum = 0;
    for (Object grade : grades) {
      sum += (double) grade;
    }
    avg = sum / grades.length;
  }


}
