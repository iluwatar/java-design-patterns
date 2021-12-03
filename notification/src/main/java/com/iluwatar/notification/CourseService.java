package com.iluwatar.notification;

/**
 * Service to register a course.
 */
public class CourseService {
  /**
   * Instantiates a CourseService.
   */
  protected CourseService() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }

  /**
  * Register a Course.
  *
  * @param course the course to be registered.
  * @return true if course is registered successfully, else false.
  */
  public Boolean registerCourse(final RegisterCourseDto course) {
    final RegisterCourse cmd = new RegisterCourse(course);
    if (!cmd.run()) {
      return true;
    }
    return false;
  }
}
