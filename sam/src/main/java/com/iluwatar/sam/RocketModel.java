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
 * Class defining model of the rocket counter. Model are the "acceptors" from "Paxos" protocol, 
 * and when the action proposes new state then model can either accept or reject the proposal.
 *
 */
public class RocketModel {

  private int counter;
  private RocketStateEngine stateEngine; 

  public RocketModel() {
  }
  
  /**
   * A method that receives a new value of the model from the action. Here, checks are made 
   * whether the state and value of the model are valid. If the model is valid, a certain change 
   * in the state or the setting of a new value of the counter will be made and as such will be 
   * passed to a certain state.
   * 
   * @param proposedModel Model value from actions
   */
  public void propose(RocketModel proposedModel) {
    if (proposedModel.getStateEngine().getCurrentState() instanceof IdleState 
        && stateEngine.getCurrentState() instanceof LaunchedState) {
      stateEngine.setIdleState();
      stateEngine.getCurrentState().render(this);
    } else if (stateEngine.getCurrentState() instanceof LaunchedState) {
      stateEngine.getCurrentState().render(this);
    } else if (proposedModel.getStateEngine().getCurrentState() instanceof ReadyState 
        && stateEngine.getCurrentState() instanceof IdleState) {
      stateEngine.setReadyState();
      stateEngine.getCurrentState().render(this);
    } else if (proposedModel.getStateEngine().getCurrentState() instanceof CountingState 
        && stateEngine.getCurrentState() instanceof ReadyState) {
      counter = proposedModel.getCounter();
      stateEngine.setCountingState();
      stateEngine.getCurrentState().render(this);
    } else if (proposedModel.getStateEngine().getCurrentState() instanceof CountingState 
        && stateEngine.getCurrentState() instanceof CountingState) {
      if (proposedModel.getCounter() == 0) {
        stateEngine.setLaunchedState();
        stateEngine.getCurrentState().render(this);
      }
      counter = proposedModel.getCounter();
      stateEngine.getCurrentState().render(this);
    } else if (proposedModel.getStateEngine().getCurrentState() instanceof AbortedState 
        && stateEngine.getCurrentState() instanceof CountingState) {
      stateEngine.setAbortedState();
      stateEngine.getCurrentState().render(this);
    } else if (proposedModel.getStateEngine().getCurrentState() instanceof AbortedState 
        && stateEngine.getCurrentState() instanceof AbortedState && proposedModel.getCounter() == 0) {
      stateEngine.setIdleState();
      stateEngine.getCurrentState().render(this);
    }
  }

  /**
   * Method for init model
   */
  public void initModel() {
    this.stateEngine = new RocketStateEngine();
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }
  
  public RocketStateEngine getStateEngine() {
    return stateEngine;
  }
}
