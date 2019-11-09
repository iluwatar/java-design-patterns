/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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
 * The circuit breaker class with all configurations.
 */
public class CircuitBreaker {
  private final long timeout;
  private final long retryTimePeriod;
  long lastFailureTime;
  int failureCount;
  private final int failureThreshold;
  private State state;
  private final long futureTime = 1000 * 1000 * 1000 * 1000;

  /**
   * Constructor to create an instance of Circuit Breaker.
   *
   * @param timeout          Timeout for the API request. Not necessary for this simple example
   * @param failureThreshold Number of failures we receive from the depended service before changing
   *                         state to 'OPEN'
   * @param retryTimePeriod  Time period after which a new request is made to remote service for
   *                         status check.
   */
  CircuitBreaker(long timeout, int failureThreshold, long retryTimePeriod) {
    // We start in a closed state hoping that everything is fine
    this.state = State.CLOSED;
    this.failureThreshold = failureThreshold;
    // Timeout for the API request.
    // Used to break the calls made to remote resource if it exceeds the limit
    this.timeout = timeout;
    this.retryTimePeriod = retryTimePeriod;
    //An absurd amount of time in future which basically indicates the last failure never happened
    this.lastFailureTime = System.nanoTime() + futureTime;
    this.failureCount = 0;
  }

  //Reset everything to defaults
  private void reset() {
    this.failureCount = 0;
    this.lastFailureTime = System.nanoTime() + futureTime;
    this.state = State.CLOSED;
  }

  private void recordFailure() {
    failureCount = failureCount + 1;
    this.lastFailureTime = System.nanoTime();
  }

  protected void setState() {
    if (failureCount > failureThreshold) { //Then something is wrong with remote service
      if ((System.nanoTime() - lastFailureTime) > retryTimePeriod) {
        //We have waited long enough and should try checking if service is up
        state = State.HALF_OPEN;
      } else {
        //Service would still probably be down
        state = State.OPEN;
      }
    } else {
      //Everything is working fine
      state = State.CLOSED;
    }
  }

  public String getState() {
    return state.name();
  }

  /**
   * Break the circuit beforehand if it is known service is down Or connect the circuit manually if
   * service comes online before expected.
   *
   * @param state State at which circuit is in
   */
  public void setStateForBypass(State state) {
    this.state = state;
  }

  /**
   * Executes service call.
   *
   * @param serviceToCall   The name of the service in String. Can be changed to data URLs in case
   *                        of web applications
   * @param serverStartTime Time at which actual server was started which makes calls to this
   *                        service
   * @return Value from the remote resource, stale response or a custom exception
   */
  public String call(String serviceToCall, long serverStartTime) throws Exception {
    setState();
    if (state == State.OPEN) {
      // return  cached response if no the circuit is in OPEN state
      return "This is stale response from API";
    } else {
      // Make the API request if the circuit is not OPEN
      if (serviceToCall.equals("delayedService")) {
        var delayedService = new DelayedService(20);
        var response = delayedService.response(serverStartTime);
        //In a real application, this would be run in a thread and the timeout
        //parameter of the circuit breaker would be utilized to know if service
        //is working. Here, we simulate that based on server response itself
        if (response.split(" ")[3].equals("working")) {
          // Yay!! the API responded fine. Let's reset everything.
          reset();
          return response;
        } else {
          // Uh-oh!! the call still failed. Let's update that in our records.
          recordFailure();
          throw new Exception("Remote service not responding");
        }
      } else {
        throw new Exception("Unknown Service Name");
      }
    }
  }
}