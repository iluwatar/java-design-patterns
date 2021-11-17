package com.iluwatar.notification;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterCourseDto extends DataTransferObject {
  private String courseId;
  private String department;
  private String semester;

  public static final Error MISSING_COURSE_ID = new Error("Course ID is missing");
  public static final Error MISSING_DEPARTMENT = new Error("Department is missing");
  public static final Error MISSING_SEMESTER = new Error("Semester is missing");

  protected RegisterCourseDto() {}


}
