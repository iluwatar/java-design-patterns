package com.iluwater.pac;

/**
 * a class used to control student average grade.
 */

public class StudentAverageGradeControl {
  private StudentAverageGradeAbstraction studentAverageGradeAbstraction = null;
  private StudentAverageGradePresentation studentAverageGradePresentation = null;
  private StudentStatusControl studentStatusControl = null;

  /**
   * constructor.
   */
  public StudentAverageGradeControl(StudentStatusControl studentStatusControl) {
    this.studentAverageGradeAbstraction = new StudentAverageGradeAbstraction();
    this.studentAverageGradePresentation = new StudentAverageGradePresentation();
    this.studentStatusControl = studentStatusControl;
  }

  /**
   * update presentation and abstraction.
   */

  public void updatePreAndAbstraction(StudentCourseAbstraction studentCourseAbstraction) {
    studentAverageGradeAbstraction.updateAvg(studentCourseAbstraction);
    studentAverageGradePresentation.present(getAvg());
    studentStatusControl.updateStatus(getAvg());
  }

  /**
   * a getter for average grade.
   *
   * @return the value of average grade.
   */
  public float getAvg() {
    return studentAverageGradeAbstraction.getAvg();
  }

  /**
   * present average grade to user.
   */
  public void present() {
    studentAverageGradePresentation.present(getAvg());
  }
}
