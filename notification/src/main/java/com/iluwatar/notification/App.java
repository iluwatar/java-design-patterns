package com.iluwatar.notification;

/**
 * A Notification is an object that collects information about
 * notificationErrors during validation of data. When an
 * notificationError appears the Notification is sent back to
 * the view to display further information about the notificationErrors.
 */
public final class App {

  /**
   * Course Id of the course to register.
   */
  private static final String COURSEID = "CSE427";

  /**
   * Semester of the course to register.
   */
  private static final String SEMESTER = "Fall21";

  /**
   * Department of the course to register.
   */
  private static final String DEPARTMENT = "Engineering";

  private App() {
  }

  /**
   * Program entry point. Example of creating a form and registering a course.
   *
   * @param args command line args.
   */
  public static void main(final String[] args) {
    final RegisterCourseForm form = new RegisterCourseForm(COURSEID, SEMESTER, DEPARTMENT);
    form.submit();

  }
}
