package com.iluwatar.notification;

public class RegisterCourseDTO extends DataTransferObject {
    private String courseID;
    private String department;
    private String semester;

    public static Error MISSING_COURSE_ID = new Error("Course ID is missing");
    public static Error UNKNOWN_COURSE_ID = new Error("This course ID is unknown");
    public static Error MISSING_DEPARTMENT = new Error("Department is missing");
    public static Error MISSING_SEMESTER = new Error("Semester is missing");
    public static Error DATE_BEFORE_COURSE_START = new Error("Semester Date is before we started providing this course");

    public String getCourseID() {
        return this.courseID;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSemester() {
        return this.semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }

}
