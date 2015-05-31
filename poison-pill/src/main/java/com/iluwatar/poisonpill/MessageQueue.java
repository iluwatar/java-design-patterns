package com.iluwatar.poisonpill;

/**
 * Represents abstraction of channel (or pipe) that bounds {@link Producer} and {@link Consumer}
 */
public interface MessageQueue extends MQPublishPoint, MQSubscribePoint {

}
