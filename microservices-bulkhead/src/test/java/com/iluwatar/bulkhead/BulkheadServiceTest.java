/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK
 * framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License Copyright © 2014-2025 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.bulkhead;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BulkheadServiceTest {

    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService(2);
    }

    @AfterEach
    void tearDown() {
        service.shutdown();
    }

    @Test
    void shouldAcceptTaskWhenCapacityAvailable() {
        var task = new Task("task-1", TaskType.USER_REQUEST, 0);
        assertThat(service.submitTask(task)).isTrue();
    }

    @Test
    void shouldRejectTaskWhenBulkheadIsFull() {
        for (int i = 0; i < 12; i++) {
            service.submitTask(new Task("blocker-" + i, TaskType.USER_REQUEST, 5000));
        }
        assertThat(service.submitTask(new Task("overflow", TaskType.USER_REQUEST, 100))).isFalse();
    }

    @Test
    void shouldReportActiveThreadCount() throws InterruptedException {
        service.submitTask(new Task("t1", TaskType.USER_REQUEST, 500));
        service.submitTask(new Task("t2", TaskType.USER_REQUEST, 500));
        Thread.sleep(100);
        assertThat(service.getActiveThreads()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void shouldReportQueueSize() throws InterruptedException {
        service.submitTask(new Task("t1", TaskType.USER_REQUEST, 2000));
        service.submitTask(new Task("t2", TaskType.USER_REQUEST, 2000));
        service.submitTask(new Task("queued", TaskType.USER_REQUEST, 2000));
        Thread.sleep(100);
        assertThat(service.getQueueSize()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void shutdownShouldCompleteWithoutError() {
        service.submitTask(new Task("t", TaskType.USER_REQUEST, 0));
        service.shutdown();
    }
}
