package com.iluwatar.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormRegisterCourse {
  private RegisterCourseDTO course;
  private CourseService service;

  private ErrorProvider errorProvider;
  private String courseId;
  private String semester;
  private String department;

  /**
  * Creates a form for registering a course.
  *
  * @param courseId the course to be added.
  * @param semester  semester of this course
  * @param department department of this course
  */
  public FormRegisterCourse(String courseId, String semester, String department) {

    this.courseId = courseId;
    this.semester = semester;
    this.department = department;

    this.service = new CourseService();
    this.errorProvider = new ErrorProvider();
  }

  /**
  * Submits a form for registering a course.
  *
  * @return "Registration Succeeded" if customer is successfully added,
  *         "Not registered, see errors" if customer already exists.
  */
  public String submit() {
    String registrationInfo = null;
    saveToCourse();
    this.service.registerCourse(this.course);
    if (this.course.getNotification().hasErrors()) {
      registrationInfo = "Not registered, see errors";
      indicateErrors();
    } else {
      registrationInfo = "Registration Succeeded";
    }
    LOGGER.info(registrationInfo);
    return registrationInfo;
  }

  private void saveToCourse() {
    this.course = new RegisterCourseDTO();
    this.course.setCourseId(this.courseId);
    this.course.setSemester(this.semester);
    this.course.setDepartment(this.department);
  }

  private void indicateErrors() {
    checkError(RegisterCourseDTO.MISSING_COURSE_ID, this.courseId);
    checkError(RegisterCourseDTO.MISSING_SEMESTER, this.semester);
    checkError(RegisterCourseDTO.MISSING_DEPARTMENT, this.department);
  }

  private void checkError(Error error, String courseId) {
    if (this.course.getNotification().getErrors().contains(error)) {
      showError(courseId, error.getErrorMessage());
    }
  }

  private void showError(String courseId, String message) {
    Error error = new Error(message + " " + courseId);
    this.errorProvider.setError(error);
  }

}
