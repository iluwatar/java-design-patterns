package com.iluwatar.publish.subscribe.subscriber;

import com.iluwatar.publish.subscribe.model.CustomerSupportContent;
import com.iluwatar.publish.subscribe.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class subscribes to CUSTOMER_SUPPORT topic
 */
@Slf4j
public class CustomerSupportSubscriber implements Subscriber {

  private static final Logger logger = LoggerFactory.getLogger(CustomerSupportSubscriber.class);
  private final String subscriberName;

  public CustomerSupportSubscriber(String subscriberName) {
    this.subscriberName = subscriberName;
  }

  @Override
  public void onMessage(Message message) {
    if (message.content() instanceof CustomerSupportContent content) {
      logger.info("Subscriber: {} sent the email to: {}", subscriberName, content.getEmail());
    } else {
      logger.error("Unknown content type: {} expected: {}", message.content().getClass(),
          CustomerSupportContent.class);
    }
  }
}
