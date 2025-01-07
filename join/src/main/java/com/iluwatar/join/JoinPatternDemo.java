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
package com.iluwatar.join;

import lombok.extern.slf4j.Slf4j;

/** Here main thread will execute after completion of 4 demo threads 
 * main thread will continue when CountDownLatch count becomes 0 
 * CountDownLatch will start with count 4 and 4 demo threads will decrease it by 1 
 * everytime when they will finish .
 */

@Slf4j
public class JoinPatternDemo {

  /**
   * execution of demo and dependent threads. 
   */
  public static void main(String[] args) {

    int[] executionOrder = {4, 2, 1, 3};
    int noOfDemoThreads = 4;
    int noOfDependentThreads = 2;
    JoinPattern pattern = new JoinPattern(noOfDemoThreads, executionOrder);
    Thread previous = null;

    for (int i = 0; i < noOfDemoThreads; i++) {
      previous = new Thread(new DemoThread(executionOrder[i], previous));
      previous.start();
    }
    pattern.await();

    //Dependent threads after execution of DemoThreads
    for (int i = 0; i < noOfDependentThreads; i++) {
      new Thread(new DependentThread(i + 1)).start();
    }
    LOGGER.info("end of program ");

  }

}
