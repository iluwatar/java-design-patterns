package com.iluwatar.servicetoworker;

/**
 * GiantController can update the giant data and redraw it using the view. Singleton object that
 * intercepts all requests and performs common functions.
 */
public class GiantController {

  private final Dispatcher dispatcher;

  /**
   * Instantiates a new Giant controller.
   *
   * @param dispatcher the dispatcher
   */
  public GiantController(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  /**
   * Sets command.
   *
   * @param s     the s
   * @param index the index
   */
  public void setCommand(Command s, int index) {
    dispatcher.performAction(s, index);
  }

  /**
   * Update view.
   *
   * @param giantModel the giant model
   */
  public void updateView(GiantModel giantModel) {
    dispatcher.updateView(giantModel);
  }
}
