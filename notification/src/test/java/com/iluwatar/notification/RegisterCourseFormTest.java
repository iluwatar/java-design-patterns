package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterCourseFormTest {

    @Test
    void testSubmitSuccess() {
        RegisterCourseForm form = new RegisterCourseForm("CSE427", "Fall21", "Engineering");
        String output = form.submit();
        assertEquals("Registration Succeeded", output);
    }

    @Test
    void testSubmitMissingCourse() {
        RegisterCourseForm form = new RegisterCourseForm("", "Fall21", "Engineering");
        String output = form.submit();
        assertEquals("Not registered, see errors", output);
    }

    @Test
    void testSubmitMissingSemester() {
        RegisterCourseForm form = new RegisterCourseForm("CSE427", "", "Engineering");
        String output = form.submit();
        assertEquals("Not registered, see errors", output);
    }

    @Test
    void testSubmitMissingDepartment() {
        RegisterCourseForm form = new RegisterCourseForm("CSE427", "Fall21", "");
        String output = form.submit();
        assertEquals("Not registered, see errors", output);
    }
}

