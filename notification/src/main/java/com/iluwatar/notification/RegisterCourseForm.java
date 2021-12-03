package com.iluwatar.notification;

import lombok.extern.slf4j.Slf4j;

/**
 * A form that users fill in to register a course.
 */
@Slf4j
public class RegisterCourseForm {

  /**
   * course for RegisterCourseForm
   */
  private transient RegisterCourseDto course;

  /**
   * service for RegisterCourseForm
   */
  private transient final CourseService service;

  /**
   * errorProvider for RegisterCourseForm
   */
  private transient final ErrorProvider errorProvider;

  /**
   * courseId for RegisterCourseForm
   */
  private transient final String courseId;

  /**
   * semester for RegisterCourseForm
   */
  private transient final String semester;

  /**
   * department for RegisterCourseForm
   */
  private transient final String department;

  /**
  * Creates a form for registering a course.
  *
  * @param courseId the course to be added.
  * @param semester  semester of this course
  * @param department department of this course
  */
  public RegisterCourseForm(final String courseId, final String semester, final String department) {

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
    String registrationInfo;
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
    this.course = new RegisterCourseDto();
    this.course.setCourseId(this.courseId);
    this.course.setSemester(this.semester);
    this.course.setDepartment(this.department);
  }

  private void indicateErrors() {
    checkError(RegisterCourseDto.MISSING_COURSE_ID, this.courseId);
    checkError(RegisterCourseDto.MISSING_SEMESTER, this.semester);
    checkError(RegisterCourseDto.MISSING_DEPARTMENT, this.department);
  }

  private void checkError(final NotificationError notificationError, final String courseId) {
    if (this.course.getNotification().getErrors().contains(notificationError)) {
      showError(courseId, notificationError.getMessage());
    }
  }

  private void showError(final String courseId, final String message) {
    final NotificationError notificationError = new NotificationError(message + " " + courseId);
    this.errorProvider.displayErrorMessage(notificationError);
  }

}
