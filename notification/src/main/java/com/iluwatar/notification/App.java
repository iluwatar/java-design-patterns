package com.iluwatar.notification;

/**
 * With the DAO pattern, we can use various method calls to
 * retrieve/add/delete/update data without directly interacting
 * with the data source. The below example demonstrates basic CRUD
 * operations: select, add, update, and delete.
 */
public final class App {

    /**
     * This is not called
     */
    private App() {
    }

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(final String[] args) {
        final String courseID = "CSE427";
        final String semester = "Fall21";
        final String department = "Engineering";
        final FormRegisterCourse form = new FormRegisterCourse(courseID, semester, department);
        form.submit();

    }
}
