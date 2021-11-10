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

    public void Submit() {
        saveToCourse();
        service.registerCourse(course);
        if (course.getNotification().hasErrors()) {
            System.out.println("Not registered, see errors");
            indicateErrors();
        }
        else{
            System.out.println("Registration Succeeded");
        }
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
