package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTransferObjectTest {

    @Test
    void testGetNotification() {

        DataTransferObject data = new DataTransferObject();
        NotificationError notificationError = new NotificationError("error");
        Notification notification = data.getNotification();
        notification.setNotificationErrors(notificationError);
        NotificationError output = data.getNotification().getNotificationErrors().get(0);
        assertEquals(notificationError, output);
    }
}
