package com.iluwatar.publish.subscribe.subscriber;

import com.iluwatar.publish.subscribe.model.Message;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** This class subscribes to WEATHER topic. */
@Slf4j
public class DelayedWeatherSubscriber implements Subscriber {

  private static final Logger logger = LoggerFactory.getLogger(DelayedWeatherSubscriber.class);

  @Override
  public void onMessage(Message message) {
    if (message.content() instanceof String content) {
      processData();
      logger.info("Delayed Weather Subscriber: {} issued message: {}", this.hashCode(), content);
    } else {
      logger.error(
          "Unknown content type: {} expected: {}",
          message.content().getClass().getSimpleName(),
          String.class.getSimpleName());
    }
  }

  /** This method will create an artificial delay to mimic the processing time. */
  private void processData() {
    try {
      TimeUnit.MILLISECONDS.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
