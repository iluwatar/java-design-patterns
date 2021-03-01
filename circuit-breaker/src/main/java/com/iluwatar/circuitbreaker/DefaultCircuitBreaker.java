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
 * The delay based Circuit breaker implementation that works in a
 * CLOSED->OPEN-(retry_time_period)->HALF_OPEN->CLOSED flow with some retry time period for failed
 * services and a failure threshold for service to open circuit.
 */
public class DefaultCircuitBreaker implements CircuitBreaker {

  private final long timeout;
  private final long retryTimePeriod;
  private final RemoteService service;
  long lastFailureTime;
  private String lastFailureResponse;
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
  DefaultCircuitBreaker(RemoteService serviceToCall, long timeout, int failureThreshold,
      long retryTimePeriod) {
    this.service = serviceToCall;
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

  // Reset everything to defaults
  @Override
  public void recordSuccess() {
    this.failureCount = 0;
    this.lastFailureTime = System.nanoTime() + futureTime;
    this.state = State.CLOSED;
  }

  @Override
  public void recordFailure(String response) {
    failureCount = failureCount + 1;
    this.lastFailureTime = System.nanoTime();
    // Cache the failure response for returning on open state
    this.lastFailureResponse = response;
  }

  // Evaluate the current state based on failureThreshold, failureCount and lastFailureTime.
  protected void evaluateState() {
    if (failureCount >= failureThreshold) { //Then something is wrong with remote service
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

  @Override
  public String getState() {
    evaluateState();
    return state.name();
  }

  /**
   * Break the circuit beforehand if it is known service is down Or connect the circuit manually if
   * service comes online before expected.
   *
   * @param state State at which circuit is in
   */
  @Override
  public void setState(State state) {
    this.state = state;
    switch (state) {
      case OPEN:
        this.failureCount = failureThreshold;
        this.lastFailureTime = System.nanoTime();
        break;
      case HALF_OPEN:
        this.failureCount = failureThreshold;
        this.lastFailureTime = System.nanoTime() - retryTimePeriod;
        break;
      default:
        this.failureCount = 0;
    }
  }

  /**
   * Executes service call.
   *
   * @return Value from the remote resource, stale response or a custom exception
   */
  @Override
  public String attemptRequest() throws RemoteServiceException {
    evaluateState();
    if (state == State.OPEN) {
      // return cached response if the circuit is in OPEN state
      return this.lastFailureResponse;
    } else {
      // Make the API request if the circuit is not OPEN
      try {
        //In a real application, this would be run in a thread and the timeout
        //parameter of the circuit breaker would be utilized to know if service
        //is working. Here, we simulate that based on server response itself
        var response = service.call();
        // Yay!! the API responded fine. Let's reset everything.
        recordSuccess();
        return response;
      } catch (RemoteServiceException ex) {
        recordFailure(ex.getMessage());
        throw ex;
      }
    }
  }
}
