package com.iluwatar.poisonpill;

/**
 * Endpoint to retrieve {@link Message} from queue
 */
public interface MQSubscribePoint {

	public Message take() throws InterruptedException;
}
