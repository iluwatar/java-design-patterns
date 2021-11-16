package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerCommandTest {
    // test submit
    @Test
    void testServerCommand() {

        DataTransferObject data = new DataTransferObject();
        ServerCommand server = new ServerCommand(data);
        Error error = new Error("error");
        Notification notification = data.getNotification();
        notification.setErrors(error);
        Error output = server.getNotification().getErrors().get(0);
        assertEquals(error, output);
    }
}
