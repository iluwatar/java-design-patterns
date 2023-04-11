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
package com.iluwatar.servicetoworker;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;

/**
 * The front controller intercepts all requests and performs common functions using decorators. The
 * front controller passes request information to the dispatcher, which uses the request and an
 * internal model to chooses and execute appropriate actions. The actions process user input,
 * translating it into appropriate updates to the model. The model applies business rules and stores
 * the data persistently. Based on the user input and results of the actions, the dispatcher chooses
 * a view. The view transforms the updated model data into a form suitable for the user.
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    // create model, view and controller
    var giant1 = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    var giant2 = new GiantModel("giant2", Health.DEAD, Fatigue.SLEEPING, Nourishment.STARVING);
    var action1 = new Action(giant1);
    var action2 = new Action(giant2);
    var view = new GiantView();
    var dispatcher = new Dispatcher(view);
    dispatcher.addAction(action1);
    dispatcher.addAction(action2);
    var controller = new GiantController(dispatcher);

    // initial display
    controller.updateView(giant1);
    controller.updateView(giant2);

    // controller receives some interactions that affect the giant
    controller.setCommand(new Command(Fatigue.SLEEPING, Health.HEALTHY, Nourishment.STARVING), 0);
    controller.setCommand(new Command(Fatigue.ALERT, Health.HEALTHY, Nourishment.HUNGRY), 1);

    // redisplay
    controller.updateView(giant1);
    controller.updateView(giant2);
    // controller receives some interactions that affect the giant
  }
}
