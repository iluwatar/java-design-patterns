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
 * Class defining state engine
 *
 */
public class StateEngine {

  private RocketState state;
  private RocketView view;
  private RocketActions actions;
  
  public StateEngine(RocketView view, RocketActions actions) {
    this.view = view;
    this.actions = actions;
  }

  /**
   * Method for render model 
   * 
   * @param model from RocketModel
   */
  public void render(RocketModel model) {
    if (model.getCounter() == 0) {
      this.changeStateToLaunched();
    } else if (model.getCounter() == 10) {
      this.changeStateToCounting();
    }

    this.view.display(this.getRepresentation(model));
    this.nextAction(model);
  }

  /**
   * Method calculates next action
   * 
   * @param model contains rocket model data
   */
  public void nextAction(RocketModel model) {
    if (model.getCounter() <= 10 && model.getCounter() > 0 && this.state.getClass().equals(CountingState.class)) {
      this.actions.decrement(); 
    }
  }

  public String getRepresentation(RocketModel model) {
    return this.state.getStateRepresentation(model);
  }

  public void changeStateToLaunched() {
    this.setState(new LaunchedState());
  }

  public void changeStateToCounting() {
    this.setState(new CountingState());
  }

  public void setState(RocketState state) {
    this.state = state;
  }
  
  public RocketState getState() {
    return this.state;
  }
}
