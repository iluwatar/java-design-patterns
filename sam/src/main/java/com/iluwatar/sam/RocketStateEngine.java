/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.sam;

/**
 * The class defines a state engine that manages the state of the rocket counter.
 * According to the Paxos protocol, a state engine is not needed, but if the solution 
 * to the problem is complex or has a large number of countries, it is wise to introduce 
 * this way of managing the situation. If the application state changes from a model, 
 * the class model becomes complex if there is no such way of managing the situation.
 *
 */
public class RocketStateEngine {
  private ReadyState readyState;
  private AbortedState abortedState;
  private IdleState idleState;
  private LaunchedState launchedState;
  private CountingState countingState;
  private RocketState currentState;
  
  /**
   * A constructor that initially creates all the states.
   */
  public RocketStateEngine() {
    this.readyState = new ReadyState();
    this.abortedState = new AbortedState();
    this.idleState = new IdleState();
    this.launchedState = new LaunchedState();
    this.countingState = new CountingState();
    this.currentState = idleState;
  }
  
  public void setReadyState() {
    this.currentState = readyState;
  }
  
  public void setAbortedState() {
    this.currentState = abortedState;
  }
  
  public void setIdleState() {
    this.currentState = idleState;
  }
  
  public void setLaunchedState() {
    this.currentState = launchedState;
  }
  
  public void setCountingState() {
    this.currentState = countingState;
  }
  
  public RocketState getCurrentState() {
    return this.currentState;
  }
}
