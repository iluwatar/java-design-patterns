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
package com.iluwatar.twin;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a UI thread for drawing the {@link BallItem}, and provide the method for suspend
 * and resume. It holds the reference of {@link BallItem} to delegate the draw task.
 */

@Slf4j
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Slf4j
public class BallThread extends Thread {

    @Setter
    private BallItem twin;

    private volatile boolean isRunning = true;

    private final CountDownLatch suspendLatch = new CountDownLatch(1);

    public void run() {
        while (isRunning) {
            try {
                suspendLatch.await(); // Wait until released
            } catch (InterruptedException e) {
                LOGGER.warning("Thread interrupted.");
                Thread.currentThread().interrupt();
            }
            twin.draw();
            twin.move();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                LOGGER.warning("Thread interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void suspendMe() {
        suspendLatch.countDown(); // Release the latch
        LOGGER.info("Thread suspended.");
    }

    public void resumeMe() {
        suspendLatch.countDown(); // In case latch was already released
        suspendLatch = new CountDownLatch(1); // Reset the latch
        LOGGER.info("Thread resumed.");
    }

    public void stopMe() {
        isRunning = false;
        suspendMe(); // Release latch to ensure the thread can terminate
    }
}
