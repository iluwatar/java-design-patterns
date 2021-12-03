package com.iluwatar.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationTest {
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

    @Test
    void setAndGetErrorsTest() {
        NotificationError err = new NotificationError("This is an error");
        notification.setErrors(err);
        List<NotificationError> notificationErrors = notification.getErrors();
        assertEquals(err, notificationErrors.get(0));
    }

    @Test
    void hasErrorTest() {
        NotificationError err = new NotificationError("This is an error");
        notification.setErrors(err);
        Boolean hasErrors = notification.hasErrors();
        assertEquals(hasErrors, true);
    }

}
