package com.iluwatar.notification;

import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for RegisterCourse
 */
@Setter
@Getter
public class RegisterCourseDto extends DataTransferObject {
  /**
   * CourseId of the course to register
   */
  private String courseId;
  /**
   * Department of the course to register
   */
  private String department;
  /**
   * Semester of the course to register
   */
  private String semester;

  /**
   * NotificationError for MISSING_COURSE_ID
   */
  public static final NotificationError MISSING_COURSE_ID =
      new NotificationError("Course ID is missing");

  /**
   * NotificationError for MISSING_DEPARTMENT
   */
  public static final NotificationError MISSING_DEPARTMENT =
      new NotificationError("Department is missing");

  /**
   * NotificationError for MISSING_SEMESTER
   */
  public static final NotificationError MISSING_SEMESTER =
      new NotificationError("Semester is missing");

  /**
   * Instantiates a RegisterCourseDto
   */
  protected RegisterCourseDto() {
    super();
  }


}
