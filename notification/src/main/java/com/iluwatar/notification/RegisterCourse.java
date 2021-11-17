package com.iluwatar.notification;

public class RegisterCourse extends ServerCommand {

  protected RegisterCourse(RegisterCourseDto course) {
    super(course);
  }

  /**
  * Runs this service to validate registration forms, if no errors reported, save to backend.
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

  protected Boolean isNullOrBlank(String inputString) {
    return inputString == null || inputString.equals("");
  }

  protected void failIfNullOrBlank(String inputString, Error error) {
    fail(isNullOrBlank(inputString), error);
  }

  protected void fail(Boolean condition, Error error) {
    if (condition) {
      this.getNotification().setErrors(error);
    }
  }
}
