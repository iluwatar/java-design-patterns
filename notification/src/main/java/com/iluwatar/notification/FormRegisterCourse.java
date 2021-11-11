package com.iluwatar.notification;

public class FormRegisterCourse {
    RegisterCourseDTO course;
    CourseService service = new CourseService();

    private ErrorProvider errorProvider = new ErrorProvider();
    private String courseID;
    private String semester;
    private String department;

    public FormRegisterCourse(String courseID, String semester, String department) {
        this.courseID = courseID;
        this.semester = semester;
        this.department = department;
    }

    public String Submit() {
        String registrationError = null;
        saveToCourse();
        service.registerCourse(this.course);
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
        course = new RegisterCourseDTO();
        course.setCourseID(this.courseID);
        course.setSemester(this.semester);
        course.setDepartment(this.department);
    }

    private void indicateErrors() {
        checkError(RegisterCourseDTO.MISSING_COURSE_ID, this.courseID);
        checkError(RegisterCourseDTO.MISSING_SEMESTER, this.semester);
        checkError(RegisterCourseDTO.MISSING_DEPARTMENT, this.department);
    }
    private void checkError (Error error, String courseID) {
        if (course.getNotification().getErrors().contains(error))
            showError(courseID, error.getErrorMessage());
    }

    void showError (String arg, String message) {
        errorProvider.setError(arg, message);
    }

}
