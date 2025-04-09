package com.iluwatar.backpressure;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.BaseSubscriber;

/**
 * This class is the custom subscriber that subscribes to the data stream.
 */
@Slf4j
public class Subscriber extends BaseSubscriber<Integer> {

  private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

  @Override
  protected void hookOnSubscribe(@NonNull Subscription subscription) {
    logger.info("subscribe()");
    request(10); //request 10 items initially
  }

  @Override
  protected void hookOnNext(@NonNull Integer value) {
    processItem();
    logger.info("process({})", value);
    if (value % 5 == 0) {
      // request for the next 5 items after processing first 5
      request(5);
    }
  }

  @Override
  protected void hookOnComplete() {
    App.latch.countDown();
  }

  private void processItem() {
    try {
      Thread.sleep(500); // simulate slow processing
    } catch (InterruptedException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
