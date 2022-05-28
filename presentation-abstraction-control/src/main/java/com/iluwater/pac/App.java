package com.iluwater.pac;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    /**
     * The main function.
     * First, set the math and physics scores, judge whether the student passes the course average score.
     * Then, modify the physics score, make it pass.
     *
     * @param args Normally no arguments
     */

    public static void main(String[] args) {
        var studentStatusControl = new StudentStatusControl();
        //CS304 Issue link: https://github.com/iluwatar/java-design-patterns/issues/304
        studentStatusControl.getStudentCourseControl().addCourseGrade("Math", 90.0f);
        studentStatusControl.getStudentCourseControl().addCourseGrade("Physics", 20.0f);
        studentStatusControl.getStudentCourseControl().present();
        studentStatusControl.getStudentAverageGradeControl().present();
        studentStatusControl.present();
        studentStatusControl.getStudentCourseControl().changeCourseGrade("Physics", 60.0f);
        LOGGER.info("After change physics' grade");
        studentStatusControl.getStudentCourseControl().present();
        studentStatusControl.getStudentAverageGradeControl().present();
        studentStatusControl.present();
    }
}
