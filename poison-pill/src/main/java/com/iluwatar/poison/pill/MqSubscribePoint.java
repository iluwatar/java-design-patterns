package com.iluwatar.poison.pill;

/**
 * Endpoint to retrieve {@link Message} from queue
 */
public interface MqSubscribePoint {

  Message take() throws InterruptedException;
}
