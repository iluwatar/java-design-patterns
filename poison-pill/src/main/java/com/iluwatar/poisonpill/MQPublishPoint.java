package com.iluwatar.poisonpill;

/**
 * Endpoint to publish {@link Message} to queue
 */
public interface MQPublishPoint {

	public void put(Message msg) throws InterruptedException;
}
