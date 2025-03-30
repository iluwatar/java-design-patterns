package com.iluwatar.publish.subscribe.subscriber;

import com.iluwatar.publish.subscribe.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** This class subscribes to CUSTOMER_SUPPORT topic. */
@Slf4j
public class CustomerSupportSubscriber implements Subscriber {

  private static final Logger logger = LoggerFactory.getLogger(CustomerSupportSubscriber.class);

  @Override
  public void onMessage(Message message) {
    if (message.content() instanceof String content) {
      logger.info(
          "Customer Support Subscriber: {} sent the email to: {}", this.hashCode(), content);
    } else {
      logger.error(
          "Unknown content type: {} expected: {}",
          message.content().getClass().getSimpleName(),
          String.class.getSimpleName());
    }
  }
}
