package com.iluwatar.notification;

public class CourseService {

    public void registerCourse (RegisterCourseDTO course) {
        RegisterCourse cmd = new RegisterCourse(course);
        cmd.run();
    }
}
