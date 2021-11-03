package com.iluwatar.notification;

public class RegisterCourse extends ServerCommand {

    public RegisterCourse(RegisterCourseDTO course) {
        super(course);
    }

    public void run() {
        validate();
        if (!super.getNotification().hasErrors()) {
//            RegisterCourseInBackendSystems();
        }
    }

    private void validate() {
        failIfNullOrBlank(((RegisterCourseDTO)this.data).getCourseID(), RegisterCourseDTO.MISSING_COURSE_ID);
        failIfNullOrBlank(((RegisterCourseDTO)this.data).getDepartment(), RegisterCourseDTO.MISSING_DEPARTMENT);
        failIfNullOrBlank(((RegisterCourseDTO)this.data).getSemester(), RegisterCourseDTO.MISSING_SEMESTER);
    }

    protected Boolean isNullOrBlank(String s) {
        return (s == null || s == "");
    }

    protected void failIfNullOrBlank (String s, Error error) {
        fail (isNullOrBlank(s), error);
    }

    protected void fail(Boolean condition, Error error) {
        if (condition == true) {
            this.getNotification().setErrors(error);
        }
    }
}
