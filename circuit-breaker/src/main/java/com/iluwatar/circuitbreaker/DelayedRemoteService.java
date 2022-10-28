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

package com.iluwatar.circuitbreaker;

/**
 * This simulates the remote service It responds only after a certain timeout period (default set to
 * 20 seconds).
 */
public class DelayedRemoteService implements RemoteService {

  private final long serverStartTime;
  private final int delay;

  /**
   * Constructor to create an instance of DelayedService, which is down for first few seconds.
   *
   * @param delay the delay after which service would behave properly, in seconds
   */
  public DelayedRemoteService(long serverStartTime, int delay) {
    this.serverStartTime = serverStartTime;
    this.delay = delay;
  }

  public DelayedRemoteService() {
    this.serverStartTime = System.nanoTime();
    this.delay = 20;
  }

  /**
   * Responds based on delay, current time and server start time if the service is down / working.
   *
   * @return The state of the service
   */
  @Override
  public String call() throws RemoteServiceException {
    var currentTime = System.nanoTime();
    //Since currentTime and serverStartTime are both in nanoseconds, we convert it to
    //seconds by diving by 10e9 and ensure floating point division by multiplying it
    //with 1.0 first. We then check if it is greater or less than specified delay and then
    //send the reply
    if ((currentTime - serverStartTime) * 1.0 / (1000 * 1000 * 1000) < delay) {
      //Can use Thread.sleep() here to block and simulate a hung server
      throw new RemoteServiceException("Delayed service is down");
    }
    return "Delayed service is working";
  }
}
