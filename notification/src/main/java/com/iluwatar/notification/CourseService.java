package com.iluwatar.notification;

public class CourseService {
    protected CourseService() {}

    /**
     * Register a Course.
     *
     * @param course the course to be registered.
     */
    public void registerCourse (RegisterCourseDTO course) {
        RegisterCourse cmd = new RegisterCourse(course);
        cmd.run();
    }
}
