package com.iluwatar.poison.pill;

/**
 * One of the possible approaches to terminate Producer-Consumer pattern is using the Poison Pill idiom.
 * If you use Poison Pill as the termination signal then Producer is responsible to notify Consumer that exchange is over
 * and reject any further messages. Consumer receiving Poison Pill will stop reading messages from the queue.
 * You must also ensure that the Poison Pill will be the last message that will be read from the queue (if you have
 * prioritized queue then this can be tricky).
 * <p>
 * In simple cases as Poison Pill can be used just null-reference, but holding unique separate shared
 * object-marker (with name "Poison" or "Poison Pill") is more clear and self describing.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
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
