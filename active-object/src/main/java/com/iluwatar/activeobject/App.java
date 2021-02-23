/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Noam Greenshtain
 * 
 * The Active Object pattern helps to solve synchronization difficulties without using 
 * 'synchronized' methods.The active object will contain a thread-safe data structure 
 * (such as BlockingQueue) and use to synchronize method calls by moving the logic of the method
 * into an invocator(usually a Runnable) and store it in the DSA.
 * 
 * In this example, we fire 20 threads to modify a value in the target class.
 *
 */
public class App {
  
  public final static int WORKERS = 20;
  
  private final static Logger LOGGER = LoggerFactory.getLogger(ActiveCounter.class.getName());

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {  
    ActiveCounter counter = new ActiveCounter();
    ExecutorService e = Executors.newCachedThreadPool();
    for(int i=0;i<WORKERS;i++) {
      e.execute(new Runnable() {      
        @Override
        public void run() {
          try {
            counter.incremenet();
            counter.printVal();
          } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
          }
        }
        
      });
    }
    try {
      e.awaitTermination(1, TimeUnit.SECONDS);
    } catch (InterruptedException e1) {
      LOGGER.error(e1.getMessage());
    }
    System.exit(1);
  }
}