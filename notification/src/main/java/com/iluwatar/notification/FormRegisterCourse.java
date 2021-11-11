package com.iluwatar.notification;

public class FormRegisterCourse {
    private RegisterCourseDTO course;
    private CourseService service;

    private ErrorProvider errorProvider;
    private String courseID;
    private String semester;
    private String department;

    public FormRegisterCourse(String courseID, String semester, String department) {

        this.courseID = courseID;
        this.semester = semester;
        this.department = department;

        this.service = new CourseService();
        this.errorProvider = new ErrorProvider();
    }

    public String Submit() {
        String registrationError = null;
        saveToCourse();
        this.service.registerCourse(this.course);
        if (this.course.getNotification().hasErrors()) {
            registrationError = "Not registered, see errors";
            indicateErrors();
        }
        else{
            registrationError ="Registration Succeeded";
        }
        System.out.println(registrationError);
        return registrationError;
    }
    private void saveToCourse() {
        this.course = new RegisterCourseDTO();
        this.course.setCourseID(this.courseID);
        this.course.setSemester(this.semester);
        this.course.setDepartment(this.department);
    }

    private void indicateErrors() {
        checkError(RegisterCourseDTO.MISSING_COURSE_ID, this.courseID);
        checkError(RegisterCourseDTO.MISSING_SEMESTER, this.semester);
        checkError(RegisterCourseDTO.MISSING_DEPARTMENT, this.department);
    }
    private void checkError (Error error, String courseID) {
        if (this.course.getNotification().getErrors().contains(error))
            showError(courseID, error.getErrorMessage());
    }

    void showError (String arg, String message) {
        this.errorProvider.setError(arg, message);
    }

}
