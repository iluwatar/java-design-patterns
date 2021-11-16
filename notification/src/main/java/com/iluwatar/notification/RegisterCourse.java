package com.iluwatar.notification;

public class RegisterCourse extends ServerCommand {

    protected RegisterCourse(RegisterCourseDTO course) {
        super(course);
    }

    /**
     * Runs this service to validate registration forms, if no errors reported, save to backend
     *
     * @return true if Notification contains errors, else false
     */
    public Boolean run() {
        Boolean containsErrors = false;
        validate();
        if (!super.getNotification().hasErrors()) {
//            RegisterCourseInBackendSystems();
        }else{
            containsErrors = true;
        }
        return containsErrors;
    }

    private void validate() {
        failIfNullOrBlank(((RegisterCourseDTO)this.data).getCourseID(), RegisterCourseDTO.MISSING_COURSE_ID);
        failIfNullOrBlank(((RegisterCourseDTO)this.data).getDepartment(), RegisterCourseDTO.MISSING_DEPARTMENT);
        failIfNullOrBlank(((RegisterCourseDTO)this.data).getSemester(), RegisterCourseDTO.MISSING_SEMESTER);
    }

    protected Boolean isNullOrBlank(String inputString) {
        return inputString == null || inputString == "";
    }

    protected void failIfNullOrBlank (String inputString, Error error) {
        fail (isNullOrBlank(inputString), error);
    }

    protected void fail(Boolean condition, Error error) {
        if (condition) {
            this.getNotification().setErrors(error);
        }
    }
}
