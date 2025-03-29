package com.iluwatar.publish.subscribe;

import com.iluwatar.publish.subscribe.model.CustomerSupportContent;
import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;
import com.iluwatar.publish.subscribe.model.TopicName;
import com.iluwatar.publish.subscribe.model.WeatherContent;
import com.iluwatar.publish.subscribe.publisher.Publisher;
import com.iluwatar.publish.subscribe.publisher.PublisherImpl;
import com.iluwatar.publish.subscribe.subscriber.CustomerSupportSubscriber;
import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber;

/**
 * <p>The Publish and Subscribe pattern is a messaging paradigm used in software architecture with
 * several key points: <li>Decoupling of publishers and subscribers: Publishers and subscribers
 * operate independently, and there's no direct link between them. This enhances the scalability and
 * * modularity of applications.</li><li>Event-driven communication: The pattern facilitates
 * event-driven architectures by allowing publishers to broadcast events without concerning
 * themselves with who receives the events.</li><li>Dynamic subscription: Subscribers can
 * dynamically choose to listen for specific events or messages they are interested in, often by
 * subscribing to a particular topic or channel.</li><li>Asynchronous processing: The pattern
 * inherently supports asynchronous message processing, enabling efficient handling of events and
 * improving application responsiveness.</li><li>Scalability: By decoupling senders and receivers,
 * the pattern can support a large number of publishers and subscribers, making it suitable for
 * scalable systems.</li><li>Flexibility and adaptability: New subscribers or publishers can be
 * added to the system without significant changes to the existing components, making the system
 * highly adaptable to evolving requirements.</li></p>
 *
 * <p>In this example we will create two {@link TopicName}s WEATHER and CUSTOMER_SUPPORT.
 * Then we will register those topics in the {@link Publisher}.
 * After that we will create two {@link WeatherSubscriber}s to WEATHER {@link Topic}.
 * Also, we will create two {@link CustomerSupportSubscriber}s to CUSTOMER_SUPPORT {@link Topic}.
 * Now we can publish the two {@link Topic}s with different content in the {@link Message}s.
 * And we can observe the output in the log where,
 * {@link WeatherSubscriber} will output the message with {@link WeatherContent}.
 * {@link CustomerSupportSubscriber} will output the message with {@link CustomerSupportContent}.
 * Each subscriber is only listening to the subscribed topic.
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    final String weatherSub1Name = "weatherSub1";
    final String weatherSub2Name = "weatherSub2";
    final String supportSub1Name = "supportSub1";
    final String supportSub2Name = "supportSub2";

    Topic weatherTopic = new Topic(TopicName.WEATHER);
    Topic supportTopic = new Topic(TopicName.CUSTOMER_SUPPORT);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(supportTopic);

    Subscriber weatherSub1 = new WeatherSubscriber(weatherSub1Name);
    Subscriber weatherSub2 = new WeatherSubscriber(weatherSub2Name);
    weatherTopic.addSubscriber(weatherSub1);
    weatherTopic.addSubscriber(weatherSub2);

    Subscriber supportSub1 = new CustomerSupportSubscriber(supportSub1Name);
    Subscriber supportSub2 = new CustomerSupportSubscriber(supportSub2Name);
    supportTopic.addSubscriber(supportSub1);
    supportTopic.addSubscriber(supportSub2);

    publisher.publish(weatherTopic, new Message(WeatherContent.earthquake));
    publisher.publish(supportTopic, new Message(CustomerSupportContent.DE));
  }
}