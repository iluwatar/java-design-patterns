package com.iluwatar.leaderfollower;

/**
 * The ConcreteEventHandler. This is used by the {@link Worker} to process the newly arrived work.
 * @author amit
 *
 */
public class ConcreteEventHandler implements EventHandler{

  @Override
  public void handleEvent(Handle handle) {
    System.out.println("Doing the work");
    System.out.println("Travelled the distance " + handle.getPayLoad());
  }

}
