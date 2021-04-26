package com.iluwatar.pac;

/**
 * a controller class.
 */
public class StudentCourseControl {
  private StudentCoursePresentation studentCoursePresentation = null;

  private StudentCourseAbstraction studentAbstraction = null;
  private StudentStatusControl studentStatusControl = null;

  /**
   * constructor.
   *
   * @param studentStatusControl is a StudentStatusControl object.
   */
  public StudentCourseControl(StudentStatusControl studentStatusControl) {
    this.studentCoursePresentation = new StudentCoursePresentation();
    this.studentAbstraction = new StudentCourseAbstraction();
    this.studentStatusControl = studentStatusControl;
  }

  /**
   * change the course grade.
   *
   * @return whether the operation succeeds or not.
   */
  public boolean changeCourseGrade(String name, float grade) {
    if (studentAbstraction.changeGrade(name, grade) == -1) {
      return false;
    }
    studentStatusControl.update(studentAbstraction);
    return true;
  }

  /**
   * add a course with its grade.
   */
  public void addCourseGrade(String name, float grade) {
    studentAbstraction.addGrade(name, grade);
    studentStatusControl.update(studentAbstraction);
  }

  /**
   * get a course grade with the course name.
   */
  public float getCourseGrade(String name) {
    return studentAbstraction.getGrade(name);
  }

  /**
   * present to the user.
   */
  public void present() {

    studentCoursePresentation.present(studentAbstraction);
  }

  /**
   * get StudentCourseAbstraction.
   * @return the StudentCourseAbstraction object that this class object holds.
   */
  public StudentCourseAbstraction getStudentAbstraction() {
    return studentAbstraction;
  }
}
