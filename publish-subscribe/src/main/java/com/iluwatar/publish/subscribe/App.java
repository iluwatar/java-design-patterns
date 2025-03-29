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

public class App {
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