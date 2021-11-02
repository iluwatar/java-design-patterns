package com.iluwatar.notification;

public class RegisterCourse extends ServerCommand{
    public RegisterCourse(RegisterCourseDTO course) {
        super(course);
    }
    public void run() {
        validate();
        if (!getnotification().hasErrors()) {
//            RegisterClaimInBackendSystems();
        }
    }

    private void validate() {


    }
}
