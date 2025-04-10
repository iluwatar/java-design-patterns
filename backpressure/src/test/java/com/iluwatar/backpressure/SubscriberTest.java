package com.iluwatar.backpressure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SubscriberTest {

  @RegisterExtension public LoggerExtension loggerExtension = new LoggerExtension();

  @Test
  public void testSubscribe() throws InterruptedException {

    Subscriber sub = new Subscriber();
    Publisher.publish(1, 8, 100).subscribe(sub);

    App.latch = new CountDownLatch(1);
    App.latch.await();

    String result = String.join(",", loggerExtension.getFormattedMessages());
    assertTrue(
        result.endsWith(
            "onSubscribe(FluxConcatMapNoPrefetch."
                + "FluxConcatMapNoPrefetchSubscriber),request(10),onNext(1),process(1),onNext(2),"
                + "process(2),onNext(3),process(3),onNext(4),process(4),onNext(5),process(5),request(5),"
                + "onNext(6),process(6),onNext(7),process(7),onNext(8),process(8),onComplete()"));
  }
}
