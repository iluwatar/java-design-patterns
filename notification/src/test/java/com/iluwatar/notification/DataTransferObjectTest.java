package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTransferObjectTest {

    @Test
    void testGetNotification() {

        DataTransferObject data = new DataTransferObject();
        Error error = new Error("error");
        Notification notification = data.getNotification();
        notification.setErrors(error);
        Error output = data.getNotification().getErrors().get(0);
        assertEquals(error, output);
    }
}
