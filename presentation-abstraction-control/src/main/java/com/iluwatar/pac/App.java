package com.iluwatar.pac;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
  /**
   * The main function.
   *
   * @param args Normally no arguments.
   */
  public static void main(String[] args) {
    var studentStatusControl = new StudentStatusControl();
    var studentCourseControl = studentStatusControl.getStudentCourseControl();
    studentCourseControl.addCourseGrade("Math", 90.0f);
    studentCourseControl.addCourseGrade("Physics", 20.0f);
    studentCourseControl.present();
    StudentAverageGradeControl studentAverageGradeControl =
            studentStatusControl.getStudentAverageGradeControl();
    studentAverageGradeControl.present();
    studentStatusControl.present();
    studentCourseControl.changeCourseGrade("Physics", 60.0f);
    LOGGER.info("After change physics' grade");
    studentCourseControl.present();
    studentAverageGradeControl.present();
    studentStatusControl.present();
  }
}
