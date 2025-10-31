/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL.
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

package com.iluwatar.activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 🧠 The ActiveCreature class represents an "Active Object" pattern.
 * Each creature has its own thread and request queue.
 * Instead of performing actions directly, we submit them as Runnables to its queue.
 * This helps separate method execution from method invocation (asynchronous behavior).
 */
public abstract class ActiveCreature {

  // Logger to print activity messages
  private static final Logger logger = LoggerFactory.getLogger(ActiveCreature.class.getName());

  // Queue to store tasks (Runnables) that the creature will execute
  private final BlockingQueue<Runnable> requests;

  // Name of the creature
  private final String name;

  // Thread on which this creature executes its actions
  private final Thread thread;

  // Status of the thread (0 = OK, non-zero = error/interrupted)
  private int status;

  /**
   * 🏗️ Constructor initializes creature name, status, and starts its own thread.
   * The thread continuously takes Runnables from the queue and executes them.
   */
  protected ActiveCreature(String name) {
    this.name = name;
    this.status = 0;
    this.requests = new LinkedBlockingQueue<>();

    // Creating and starting a new thread for this creature
    thread = new Thread(() -> {
      boolean running = true;
      while (running) {
        try {
          // Take next task from the queue and execute it
          requests.take().run();
        } catch (InterruptedException e) {
          // If thread interrupted, log and stop the loop
          if (this.status != 0) {
            logger.error("Thread was interrupted. --> {}", e.getMessage());
          }
          running = false;
          Thread.currentThread().interrupt();
        }
      }
    });

    // Start the creature's background thread
    thread.start();
  }

  /**
   * 🍲 The creature eats asynchronously.
   * Instead of executing immediately, a Runnable is added to the queue.
   */
  public void eat() throws InterruptedException {
    requests.put(() -> {
      logger.info("{} is eating!", name());
      try {
        Thread.sleep(1000); // simulate eating delay
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      logger.info("{} has finished eating!", name());
    });
  }

  /**
   * 🚶 The creature roams asynchronously.
   */
  public void roam() throws InterruptedException {
    requests.put(() -> {
      logger.info("{} has started to roam in the wastelands.", name());
      try {
        Thread.sleep(1500); // simulate roaming delay
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      logger.info("{} has stopped roaming.", name());
    });
  }

  /**
   * 📛 Returns the name of the creature.
   */
  public String name() {
    return this.name;
  }

  /**
   * 💀 Kills the thread of execution.
   * @param status 0 = OK, other values indicate errors or manual stop.
   */
  public void kill(int status) {
    this.status = status;
    this.thread.interrupt(); // stops the creature's thread
  }

  /**
   * 📊 Returns the status of the creature's thread.
   */
  public int getStatus() {
    return this.status;
  }
}
