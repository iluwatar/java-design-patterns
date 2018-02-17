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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Class defining available rocket counter actions. The actions started from the display, 
 * or after the event on the display, a specific action for this event is started.
 */
public class RocketActions {

  private RocketModel model;
  private static final int COUNTER_MAX = 10;
  private Timer timerCounting;
  private Timer timerAbort;
  private Timer timerIdle;
  private int counter;
  private int counterAbort;
  private int counterIdle;

  public RocketActions(RocketModel model) {
    this.model = model;
  }

  /**
   * Action triggering a counter. A timer that counts for 10 seconds starts. 
   */
  public void startAction() {
    
    counter = COUNTER_MAX;
    RocketModel newModel = new RocketModel();
    newModel.initModel();
    newModel.getStateEngine().setCountingState();
    timerCounting = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (counter == 0) {
          timerCounting.stop();
        }
        counter--;
        newModel.setCounter(counter);
        model.propose(newModel);
      }
    });
    timerCounting.start();
  }
  
  /**
   * AAn action that interrupts the work of the counter. A 3-second timer starts to simulate the time 
   * required to stop the launch of the missile.
   */
  public void abortAction() {
    timerCounting.stop();
    counterAbort = 3;
    RocketModel newModel = new RocketModel();
    newModel.initModel();
    newModel.getStateEngine().setAbortedState();
    timerAbort = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
        if (counterAbort == 0) {
          timerAbort.stop();
        }
        counterAbort--;
        newModel.setCounter(counterAbort);
        model.propose(newModel);
      }
    });
    timerAbort.start();
  }
  
  /**
   * Action to switch from inactive mode to ready countdown mode.
   */
  public void prepareAction() {
    RocketModel newModel = new RocketModel();
    newModel.initModel();
    newModel.getStateEngine().setReadyState();
    model.propose(newModel);
  }

  /**
   * 
   * @return rocket model
   */
  public RocketModel getRocketModel() {
    return this.model;
  }
  
  /**
   * The action that moves the counter into the inactive state after the launch rocket 
   * and the counter completed the countdown.
   */
  public void goToIdleAction() {
    counterIdle = 3;
    timerIdle = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
        if (counterIdle == 0) {
          RocketModel newModel = new RocketModel();
          newModel.initModel();
          newModel.getStateEngine().setIdleState();
          model.propose(newModel);
          timerIdle.stop();
        }
        counterIdle--;
      }
    });
    timerIdle.start();
  }

}
