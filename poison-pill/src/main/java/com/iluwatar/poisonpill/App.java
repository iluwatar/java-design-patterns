package com.iluwatar.poisonpill;

/**
 * One of possible approaches to terminate Producer-Consumer pattern is using PoisonPill idiom.
 * If you use PoisonPill as termination signal then Producer is responsible to notify Consumer that exchange is over
 * and reject any further messages. Consumer receiving PoisonPill will stop to read messages from queue.
 * You also must ensure that PoisonPill will be last message that will be read from queue (if you have
 * prioritized queue than this can be tricky).
 * In simple cases as PoisonPill can be used just null-reference, but holding unique separate shared
 * object-marker (with name "Poison" or "PoisonPill") is more clear and self describing.
 */
public class App {

	public static void main(String[] args) {
		MessageQueue queue = new SimpleMessageQueue(10000);

		final Producer producer = new Producer("PRODUCER_1", queue);
		final Consumer consumer = new Consumer("CONSUMER_1", queue);

		new Thread() {
			@Override
			public void run() {
				consumer.consume();
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				producer.send("hand shake");
				producer.send("some very important information");
				producer.send("bye!");
				producer.stop();
			}
		}.start();
	}
}
