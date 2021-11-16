package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormRegisterCourseTest {

    @Test
    void testSubmitSuccess() {
        FormRegisterCourse form = new FormRegisterCourse("CSE427", "Fall21", "Engineering");
        String output = form.submit();
        assertEquals("Registration Succeeded", output);
    }

    @Test
    void testSubmitMissingCourse() {
        FormRegisterCourse form = new FormRegisterCourse("", "Fall21", "Engineering");
        String output = form.submit();
        assertEquals("Not registered, see errors", output);
    }

    @Test
    void testSubmitMissingSemester() {
        FormRegisterCourse form = new FormRegisterCourse("CSE427", "", "Engineering");
        String output = form.submit();
        assertEquals("Not registered, see errors", output);
    }

    @Test
    void testSubmitMissingDepartment() {
        FormRegisterCourse form = new FormRegisterCourse("CSE427", "Fall21", "");
        String output = form.submit();
        assertEquals("Not registered, see errors", output);
    }
}

