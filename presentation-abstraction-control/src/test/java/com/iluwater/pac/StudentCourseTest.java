package com.iluwater.pac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentCourseTest {
    String courseName="Math";
    float grade=90.0f;
    @Test
    void testAddCourseGrade(){
        StudentStatusControl studentStatusControl=new StudentStatusControl();
        StudentCourseControl studentCourseControl=studentStatusControl.getStudentCourseControl();

        studentCourseControl.addCourseGrade(courseName,grade);
        assertEquals(studentCourseControl.getCourseGrade(courseName),grade);
    }
    @Test
    void testChangeCourseGrade_true(){
        StudentStatusControl studentStatusControl=new StudentStatusControl();
        StudentCourseControl studentCourseControl=studentStatusControl.getStudentCourseControl();
        studentCourseControl.addCourseGrade(courseName,grade);
        float newGrade=70.0f;
        boolean isSucess=studentCourseControl.changeCourseGrade(courseName,newGrade);
        assert isSucess;
        assertEquals(studentCourseControl.getCourseGrade(courseName),newGrade);
    }
    @Test
    void testChangeCourseGrade_false(){
        StudentStatusControl studentStatusControl=new StudentStatusControl();
        StudentCourseControl studentCourseControl=studentStatusControl.getStudentCourseControl();
        studentCourseControl.addCourseGrade(courseName,grade);
        float newGrade=70.0f;
        var testCourse="Science";
        boolean isSucess=studentCourseControl.changeCourseGrade(testCourse,newGrade);
        assert !isSucess;
    }
    @Test
    void testCreation(){
        StudentCourseControl studentCourseControl=new StudentCourseControl(new StudentStatusControl());
        assertEquals(studentCourseControl.getClass(),StudentCourseControl.class);
    }
}
