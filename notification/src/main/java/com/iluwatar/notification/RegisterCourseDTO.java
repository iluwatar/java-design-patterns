package com.iluwatar.notification;

public class RegisterCourseDTO extends DataTransferObject {
    private String courseID;
    private String department;
    private String semester;

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
