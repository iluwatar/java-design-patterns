package com.iluwatar.leaderfollower;
/**
 * The EventHandler interface. All concrete EventHanlder implementations can process a unit of work
 * represented by Handle
 * @author amit
 *
 */
public interface EventHandler {
  public void handleEvent(Handle work);
}
