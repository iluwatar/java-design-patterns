/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
