package com.iluwatar.notification;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterCourseDto extends DataTransferObject {
  private String courseId;
  private String department;
  private String semester;

  public static final NotificationError MISSING_COURSE_ID = new NotificationError("Course ID is missing");
  public static final NotificationError MISSING_DEPARTMENT = new NotificationError("Department is missing");
  public static final NotificationError MISSING_SEMESTER = new NotificationError("Semester is missing");

  protected RegisterCourseDto() {}


}
