package com.iluwater.pac;

/**
 * a controller class.
 */

public class StudentCourseControl {
  private StudentCoursePresentation studentCoursePresentation = null;
  private StudentCourseAbstraction studentCourseAbstraction = null;
  private StudentStatusControl studentStatusControl = null;

  /**
   * constructor.
   *
   * @param studentStatusControl is a StudentStatusControl object.
   */
  public StudentCourseControl(StudentStatusControl studentStatusControl) {
    this.studentCoursePresentation = new StudentCoursePresentation();
    this.studentCourseAbstraction = new StudentCourseAbstraction();
    this.studentStatusControl = studentStatusControl;
  }

  /**
   * change the course grade.
   *
   * @return whether the operation succeeds or not.
   */
  public boolean changeCourseGrade(String name, float grade) {
    if (studentCourseAbstraction.changeGrade(name, grade) == -1) {
      return false;
    }
    studentStatusControl.update(studentCourseAbstraction);
    return true;
  }

  /**
   * add a course with its grade.
   */
  public void addCourseGrade(String name, float grade) {
    studentCourseAbstraction.addGrade(name, grade);
    studentStatusControl.update(studentCourseAbstraction);
  }

  /**
   * get a course grade with the course name.
   */
  public double getCourseGrade(String name) {
    return studentCourseAbstraction.getGrade(name);
  }

  /**
   * present to the user.
   */
  public void present() {
    StudentCoursePresentation.present(studentCourseAbstraction);
  }

  /**
   * get StudentCourseAbstraction.
   *
   * @return the StudentCourseAbstraction object that this class object holds.
   */
  public StudentCourseAbstraction getStudentAbstraction() {
    return studentCourseAbstraction;
  }
}
