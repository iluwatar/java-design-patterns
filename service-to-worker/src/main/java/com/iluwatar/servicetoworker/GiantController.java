package com.iluwatar.servicetoworker;

import lombok.Getter;

/**
 * GiantController can update the giant data and redraw it using the view. Singleton object that
 * intercepts all requests and performs common functions.
 */
public class GiantController {

  public Dispatcher dispatcher;

  /**
   * Instantiates a new Giant controller.
   *
   * @param dispatcher the dispatcher
   */
  public GiantController(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  /**
   * Sets command to control the dispatcher.
   *
   * @param s     the s
   * @param index the index
   */
  public void setCommand(Command s, int index) {
    dispatcher.performAction(s, index);
  }

  /**
   * Update view. This is a simple implementation, in fact, View can be implemented in a concrete
   * way
   *
   * @param giantModel the giant model
   */
  public void updateView(GiantModel giantModel) {
    dispatcher.updateView(giantModel);
  }
}
