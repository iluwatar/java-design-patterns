package com.iluwatar.notification;

/**
 * Registers a course and validate the registration forms.
 */
public class RegisterCourse extends ServerCommand {

  /**
   * Instantiates a RegisterCourse
   */
  protected RegisterCourse(final RegisterCourseDto course) {
    super(course);
  }

  /**
  * Runs this service to validate registration forms,
   * if no errors reported, save to backend.
  *
  * @return true if Notification contains errors, else false
  */
  public Boolean run() {
    Boolean containsErrors = false;
    validate();
    if (!super.getNotification().hasErrors()) {
    //       RegisterCourseInBackendSystems();
    } else {
      containsErrors = true;
    }
    return containsErrors;
  }

  private void validate() {
    failIfNullOrBlank(((RegisterCourseDto) this.data).getCourseId(),
        RegisterCourseDto.MISSING_COURSE_ID);
    failIfNullOrBlank(((RegisterCourseDto) this.data).getDepartment(),
        RegisterCourseDto.MISSING_DEPARTMENT);
    failIfNullOrBlank(((RegisterCourseDto) this.data).getSemester(),
        RegisterCourseDto.MISSING_SEMESTER);
  }

  /**
   * Checks is the input is null or blank
   *
   * @param inputString
   * @return true if the inputString is null or blank, else false
   */
  protected Boolean isNullOrBlank(final String inputString) {
    return inputString == null || inputString.equals("");
  }

  /**
   * Fails if the inputString is null or blank
   *
   * @param inputString, notificationError
   */
  protected void failIfNullOrBlank(final String inputString, final NotificationError notificationError) {
    fail(isNullOrBlank(inputString), notificationError);
  }

  /**
   * Sets NotificationError to the Notification
   *
   * @param condition, notificationError
   */
  protected void fail(final Boolean condition, final NotificationError notificationError) {
    if (condition) {
      this.getNotification().setNotificationErrors(notificationError);
    }
  }
}
