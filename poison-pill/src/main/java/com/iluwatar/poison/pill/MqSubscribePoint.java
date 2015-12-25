package com.iluwatar.poison.pill;

/**
 * Endpoint to retrieve {@link Message} from queue
 */
public interface MqSubscribePoint {

  public Message take() throws InterruptedException;
}
