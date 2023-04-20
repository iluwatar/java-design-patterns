package com.iluwatar.servicetoworker;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * The type Dispatcher, which encapsulates worker and view selection based on request information
 * and/or an internal navigation model.
 */
public class Dispatcher {

  @Getter
  private final GiantView giantView;
  private final List<Action> actions;

  /**
   * Instantiates a new Dispatcher.
   *
   * @param giantView the giant view
   */
  public Dispatcher(GiantView giantView) {
    this.giantView = giantView;
    this.actions = new ArrayList<>();
  }

  /**
   * Add an action.
   *
   * @param action the action
   */
  void addAction(Action action) {
    actions.add(action);
  }

  /**
   * Perform an action.
   *
   * @param s           the s
   * @param actionIndex the action index
   */
  public void performAction(Command s, int actionIndex) {
    actions.get(actionIndex).updateModel(s);
  }

  /**
   * Update view.
   *
   * @param giantModel the giant model
   */
  public void updateView(GiantModel giantModel) {
    giantView.displayGiant(giantModel);
  }

}
