package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseServiceTest {

    @Test
    void testCourseServiceTestSuccess() {
        RegisterCourseDto registerDTO = new RegisterCourseDto();
        registerDTO.setDepartment("English");
        registerDTO.setCourseId("CS444");
        registerDTO.setSemester("Fall21");
        CourseService course = new CourseService();
        Boolean isRegistered = course.registerCourse(registerDTO);
        assertTrue(isRegistered);
    }

    @Test
    void testCourseServiceTestFail() {
        RegisterCourseDto registerDTO = new RegisterCourseDto();
        registerDTO.setDepartment("");
        registerDTO.setCourseId("CS444");
        registerDTO.setSemester("Fall21");
        CourseService course = new CourseService();
        Boolean isRegistered = course.registerCourse(registerDTO);
        assertEquals(isRegistered, false);
    }
}
