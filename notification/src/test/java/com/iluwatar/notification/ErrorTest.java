package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {

    @Test
    void testGetErrorMessage() {
        NotificationError notificationError = new NotificationError("hello this is a test message");
        String output = notificationError.getMessage();
        assertEquals("hello this is a test message", output);
    }

    @Test
    void testEmptyError() {
        NotificationError notificationError = new NotificationError("");
        String output = notificationError.getMessage();
        assertEquals("", output);
    }
}
