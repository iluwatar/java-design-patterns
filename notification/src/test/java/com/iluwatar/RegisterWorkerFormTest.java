package com.iluwatar;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class RegisterWorkerFormTest {

    private RegisterWorkerForm registerWorkerForm;

    @Test
    void submitSuccessfully() {
        // Ensure the worker is null initially
        registerWorkerForm = new RegisterWorkerForm("John Doe", "Engineer", LocalDate.of(1990, 1, 1));

        assertNull(registerWorkerForm.worker);

        // Submit the form
        registerWorkerForm.submit();

        // Verify that the worker is not null after submission
        assertNotNull(registerWorkerForm.worker);

        // Verify that the worker's properties are set correctly
        assertEquals("John Doe", registerWorkerForm.worker.getName());
        assertEquals("Engineer", registerWorkerForm.worker.getOccupation());
        assertEquals(LocalDate.of(1990, 1, 1), registerWorkerForm.worker.getDateOfBirth());
    }

    @Test
    void submitWithErrors() {
        // Set up the worker with a notification containing errors
        registerWorkerForm = new RegisterWorkerForm(null, null, null);

        // Submit the form
        registerWorkerForm.submit();

        // Verify that the worker's properties remain unchanged
        assertNull(registerWorkerForm.worker.getName());
        assertNull(registerWorkerForm.worker.getOccupation());
        assertNull(registerWorkerForm.worker.getDateOfBirth());

        // Verify the presence of errors
        assertEquals(registerWorkerForm.worker.getNotification().getErrors().size(), 4);
    }

}
