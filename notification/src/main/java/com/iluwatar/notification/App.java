package com.iluwatar.notification;

/**
 * A Notification is an object that collects information about
 * notificationErrors during validation of data. When an
 * notificationError appears the Notification is sent back to
 * the view to display further information about the notificationErrors.
 */
public final class App {

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
