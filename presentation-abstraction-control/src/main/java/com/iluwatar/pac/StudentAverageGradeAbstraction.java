package com.iluwatar.pac;

public class StudentAverageGradeAbstraction {
  private float avg = 0f;

  /**
   * a getter.
   *
   * @return the value of average grade.
   */
  public float getAvg() {
    return avg;
  }

  /**
   * calculate and update the average grade according to the given parameter.
   *
   * @param studentCourseAbstraction is an object that contains the data of all course grades.
   */
  public void updateAvg(StudentCourseAbstraction studentCourseAbstraction) {
    Object[] grades = studentCourseAbstraction.getGrades();
    float sum = 0;
    for (int i = 0; i < grades.length; i++) {
      sum += (float) grades[i];
    }
    avg = sum / grades.length;
  }


}
