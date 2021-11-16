package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseServiceTest {

    @Test
    void testCourseServiceTest() {
        RegisterCourseDTO registerDTO = new RegisterCourseDTO();
        registerDTO.setDepartment("English");
        registerDTO.setCourseID("CS444");
        registerDTO.setSemester("Fall21");
        CourseService course = new CourseService();
        Boolean isRegistered = course.registerCourse(registerDTO);
        assertTrue(isRegistered);
    }
}
