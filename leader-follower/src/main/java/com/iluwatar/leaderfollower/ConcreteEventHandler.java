package com.iluwatar.leaderfollower;

public class ConcreteEventHandler implements EventHandler{

  @Override
  public void handleEvent(Handle handle) {
    System.out.println("Doing the work");
    System.out.println("Travelled the distance " + handle.getPayLoad());
  }

}
