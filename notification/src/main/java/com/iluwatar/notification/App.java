package com.iluwatar.notification;

public class App {
    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        RegisterCourseDTO registerCourseDTO = new RegisterCourseDTO();
        registerCourseDTO.setCourseID("CS427");
        registerCourseDTO.setDepartment("Engineering");
        registerCourseDTO.setSemester("Fall2021");
        RegisterCourse registerCourse = new RegisterCourse(registerCourseDTO);
        registerCourse.run();
    }
}
