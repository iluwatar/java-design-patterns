package com.iluwatar.publish.subscribe.subscriber;

import com.iluwatar.publish.subscribe.model.CustomerSupportContent;
import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.WeatherContent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class WeatherSubscriber implements Subscriber {

  private static final Logger logger = LoggerFactory.getLogger(WeatherSubscriber.class);
  private final String subscriberName;

  public WeatherSubscriber(String subscriberName) {
    this.subscriberName = subscriberName;
  }

  @Override
  public void onMessage(Message message) {
    if (message.content() instanceof WeatherContent content) {
      logger.info("Subscriber: {} issued message: {}", subscriberName, content.getMessage());
    } else {
      logger.error("Unknown content type: {} expected: {}", message.content().getClass(),
          CustomerSupportContent.class);
    }
  }
}
