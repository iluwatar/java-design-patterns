package com.iluwatar.publish.subscribe;

import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;
import com.iluwatar.publish.subscribe.publisher.Publisher;
import com.iluwatar.publish.subscribe.publisher.PublisherImpl;
import com.iluwatar.publish.subscribe.subscriber.CustomerSupportSubscriber;
import com.iluwatar.publish.subscribe.subscriber.DelayedWeatherSubscriber;
import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber;
import java.util.concurrent.TimeUnit;

/**
 * The Publish and Subscribe pattern is a messaging paradigm used in software architecture with
 * several key points:
 * <li>Decoupling of publishers and subscribers: Publishers and subscribers operate independently,
 *     and there's no direct link between them. This enhances the scalability and * modularity of
 *     applications.
 * <li>Event-driven communication: The pattern facilitates event-driven architectures by allowing
 *     publishers to broadcast events without concerning themselves with who receives the events.
 * <li>Dynamic subscription: Subscribers can dynamically choose to listen for specific events or
 *     messages they are interested in, often by subscribing to a particular topic or channel.
 * <li>Asynchronous processing: The pattern inherently supports asynchronous message processing,
 *     enabling efficient handling of events and improving application responsiveness.
 * <li>Scalability: By decoupling senders and receivers, the pattern can support a large number of
 *     publishers and subscribers, making it suitable for scalable systems.
 * <li>Flexibility and adaptability: New subscribers or publishers can be added to the system
 *     without significant changes to the existing components, making the system highly adaptable to
 *     evolving requirements.
 *
 *     <p>In this example we will create three topics WEATHER, TEMPERATURE and CUSTOMER_SUPPORT.
 *     Then we will register those topics in the {@link Publisher}. After that we will create two
 *     {@link WeatherSubscriber}s, one {@link DelayedWeatherSubscriber} and two {@link
 *     CustomerSupportSubscriber}.The subscribers will subscribe to the relevant topics. One {@link
 *     WeatherSubscriber} will subscribe to two topics (WEATHER, TEMPERATURE). {@link
 *     DelayedWeatherSubscriber} has a delay in message processing. Now we can publish the three
 *     {@link Topic}s with different content in the {@link Message}s. And we can observe the output
 *     in the log where, one {@link WeatherSubscriber} will output the message with weather and the
 *     other {@link WeatherSubscriber} will output weather and temperature. {@link
 *     CustomerSupportSubscriber}s will output the message with customer support email. {@link
 *     DelayedWeatherSubscriber} has a delay in processing and will output the message at last. Each
 *     subscriber is only listening to the subscribed topics.
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) throws InterruptedException {

    final String topicWeather = "WEATHER";
    final String topicTemperature = "TEMPERATURE";
    final String topicCustomerSupport = "CUSTOMER_SUPPORT";

    // 1. create the publisher.
    Publisher publisher = new PublisherImpl();

    // 2. define the topics and register on publisher
    Topic weatherTopic = new Topic(topicWeather);
    publisher.registerTopic(weatherTopic);

    Topic temperatureTopic = new Topic(topicTemperature);
    publisher.registerTopic(temperatureTopic);

    Topic supportTopic = new Topic(topicCustomerSupport);
    publisher.registerTopic(supportTopic);

    // 3. Create the subscribers and subscribe to the relevant topics
    // weatherSub1 will subscribe to two topics WEATHER and TEMPERATURE.
    Subscriber weatherSub1 = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSub1);
    temperatureTopic.addSubscriber(weatherSub1);

    // weatherSub2 will subscribe to WEATHER topic
    Subscriber weatherSub2 = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSub2);

    // delayedWeatherSub will subscribe to WEATHER topic
    // NOTE :: DelayedWeatherSubscriber has a 0.2 sec delay of processing message.
    Subscriber delayedWeatherSub = new DelayedWeatherSubscriber();
    weatherTopic.addSubscriber(delayedWeatherSub);

    // subscribe the customer support subscribers to the CUSTOMER_SUPPORT topic.
    Subscriber supportSub1 = new CustomerSupportSubscriber();
    supportTopic.addSubscriber(supportSub1);
    Subscriber supportSub2 = new CustomerSupportSubscriber();
    supportTopic.addSubscriber(supportSub2);

    // 4. publish message from each topic
    publisher.publish(weatherTopic, new Message("earthquake"));
    publisher.publish(temperatureTopic, new Message("23C"));
    publisher.publish(supportTopic, new Message("support@test.de"));

    // 5. unregister subscriber from TEMPERATURE topic
    temperatureTopic.removeSubscriber(weatherSub1);

    // 6. publish message under TEMPERATURE topic
    publisher.publish(temperatureTopic, new Message("0C"));

    /*
     * Finally, we wait for the subscribers to consume messages to check the output.
     * The output can change on each run, depending on how long the execution on each
     * subscriber would take
     * Expected behavior:
     * - weatherSub1 will consume earthquake and 23C
     * - weatherSub2 will consume earthquake
     * - delayedWeatherSub will take longer and consume earthquake
     * - supportSub1, supportSub2 will consume support@test.de
     * - the message 0C will not be consumed because weatherSub1 unsubscribed from TEMPERATURE topic
     */
    TimeUnit.SECONDS.sleep(2);
  }
}
