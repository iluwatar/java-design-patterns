package com.iluwatar.notification;

public class CourseService {
  protected CourseService() {}

  /**
  * Register a Course.
  *
  * @param course the course to be registered.
  * @return true if course is registered successfully, else false
  */
  public Boolean registerCourse(RegisterCourseDTO course) {
    Boolean isRegistered = false;
    RegisterCourse cmd = new RegisterCourse(course);
    if (!cmd.run()) {
      isRegistered = true;
    }
    return isRegistered;
  }
}
