package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterCourseTest {
    // test submit
    @Test
    void testValidate() {
        FormRegisterCourse form = new FormRegisterCourse("CSE427", "Fall21", "Engineering");

        String output = form.submit();

        assertEquals("Registration Succeeded", output);
    }
}
