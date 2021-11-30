package com.iluwatar.notification;

public final class App {

  /**
   * A Notification is an object that collects information about errors during validation of data.
   * When an error appears the Notification is sent back to the view to display further information about the errors.
   */
  private App() {
  }

  /**
   * Program entry point. Example of creating a form and registering a course.
   *
   * @param args command line args
   */
  public static void main(final String[] args) {
    final var courseId = "CSE427";
    final var semester = "Fall21";
    final var department = "Engineering";
    final RegisterCourseForm form = new RegisterCourseForm(courseId, semester, department);
    form.submit();

  }
}
