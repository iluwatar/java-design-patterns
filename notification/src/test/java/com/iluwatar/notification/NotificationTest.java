package com.iluwatar.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NotificationTest {
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

    @Test
    void setAndGetErrorsTest() {
        Error err = new Error("This is an error");
        notification.setErrors(err);
        List<Error> errors = notification.getErrors();
        assertEquals(err, errors.get(0));
    }

    @Test
    void hasErrorTest() {
        Error err = new Error("This is an error");
        notification.setErrors(err);
        Boolean hasErrors = notification.hasErrors();
        assertEquals(hasErrors, true);
    }

}
