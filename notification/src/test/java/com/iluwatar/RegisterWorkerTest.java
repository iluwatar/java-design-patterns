package com.iluwatar;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RegisterWorkerTest {

    @Test
    void runSuccessfully() {
        RegisterWorkerDto validWorkerDto = createValidWorkerDto();
        validWorkerDto.setupWorkerDto("name", "occupation", LocalDate.of(2000, 12, 1));
        RegisterWorker registerWorker = new RegisterWorker(validWorkerDto);

        // Run the registration process
        registerWorker.run();

        // Verify that there are no errors in the notification
        assertFalse(registerWorker.getNotification().hasErrors());
    }

    @Test
    void runWithMissingName() {
        RegisterWorkerDto workerDto = createValidWorkerDto();
        workerDto.setupWorkerDto(null, "occupation", LocalDate.of(2000, 12, 1));
        RegisterWorker registerWorker = new RegisterWorker(workerDto);

        // Run the registration process
        registerWorker.run();

        // Verify that the notification contains the missing name error
        assertTrue(registerWorker.getNotification().hasErrors());
        assertTrue(registerWorker.getNotification().getErrors().contains(RegisterWorkerDto.MISSING_NAME));
        assertEquals(registerWorker.getNotification().getErrors().size(), 1);
    }

    @Test
    void runWithMissingOccupation() {
        RegisterWorkerDto workerDto = createValidWorkerDto();
        workerDto.setupWorkerDto("name", null, LocalDate.of(2000, 12, 1));
        RegisterWorker registerWorker = new RegisterWorker(workerDto);

        // Run the registration process
        registerWorker.run();

        // Verify that the notification contains the missing occupation error
        assertTrue(registerWorker.getNotification().hasErrors());
        assertTrue(registerWorker.getNotification().getErrors().contains(RegisterWorkerDto.MISSING_OCCUPATION));
        assertEquals(registerWorker.getNotification().getErrors().size(), 1);
    }

    @Test
    void runWithMissingDOB() {
        RegisterWorkerDto workerDto = createValidWorkerDto();
        workerDto.setupWorkerDto("name", "occupation", null);
        RegisterWorker registerWorker = new RegisterWorker(workerDto);

        // Run the registration process
        registerWorker.run();

        // Verify that the notification contains the missing DOB error
        assertTrue(registerWorker.getNotification().hasErrors());
        assertTrue(registerWorker.getNotification().getErrors().contains(RegisterWorkerDto.MISSING_DOB));
        assertEquals(registerWorker.getNotification().getErrors().size(), 2);
    }

    @Test
    void runWithUnderageDOB() {
        RegisterWorkerDto workerDto = createValidWorkerDto();
        workerDto.setDateOfBirth(LocalDate.now().minusYears(17)); // Under 18
        workerDto.setupWorkerDto("name", "occupation", LocalDate.now().minusYears(17));
        RegisterWorker registerWorker = new RegisterWorker(workerDto);

        // Run the registration process
        registerWorker.run();

        // Verify that the notification contains the underage DOB error
        assertTrue(registerWorker.getNotification().hasErrors());
        assertTrue(registerWorker.getNotification().getErrors().contains(RegisterWorkerDto.DOB_TOO_SOON));
        assertEquals(registerWorker.getNotification().getErrors().size(), 1);
    }

    private RegisterWorkerDto createValidWorkerDto() {
        return new RegisterWorkerDto();
    }
}
