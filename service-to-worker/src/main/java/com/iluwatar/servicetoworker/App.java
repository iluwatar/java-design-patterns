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
