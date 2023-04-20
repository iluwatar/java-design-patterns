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
package com.iluwatar.activeobject;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Active Object pattern helps to solve synchronization difficulties without using 
 * 'synchronized' methods. The active object will contain a thread-safe data structure 
 * (such as BlockingQueue) and use to synchronize method calls by moving the logic of the method
 * into an invocator(usually a Runnable) and store it in the DSA.
 * 
 * <p>In this example, we fire 20 threads to modify a value in the target class.
 */
public class App implements Runnable {
  
  private static final Logger logger = LoggerFactory.getLogger(App.class.getName());
  
  private static final int NUM_CREATURES = 3;

  /**
   * Program entry point.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {  
    var app = new App();
    app.run();
  }
  
  @Override
  public void run() {
    List<ActiveCreature> creatures = new ArrayList<>();
    try {
      for (int i = 0; i < NUM_CREATURES; i++) {
        creatures.add(new Orc(Orc.class.getSimpleName() + i));
        creatures.get(i).eat();
        creatures.get(i).roam();
      }
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    } finally {
      for (int i = 0; i < NUM_CREATURES; i++) {
        creatures.get(i).kill(0);
      }
    }
  }
}
