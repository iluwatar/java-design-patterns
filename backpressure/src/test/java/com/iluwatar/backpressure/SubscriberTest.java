package com.iluwatar.backpressure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SubscriberTest {

  @RegisterExtension
  public LoggerExtension loggerExtension = new LoggerExtension();

  @Test
  public void testSubscribe() throws InterruptedException {
    App.latch = new CountDownLatch(1);
    Subscriber sub = new Subscriber();
    Publisher.publish(1, 8, 100).subscribe(sub);

    App.latch.await();
    assertEquals(22, loggerExtension.getFormattedMessages().size());
    assertEquals("subscribe()", loggerExtension.getFormattedMessages().get(2));
    assertEquals("request(10)", loggerExtension.getFormattedMessages().get(3));
    assertEquals("request(5)", loggerExtension.getFormattedMessages().get(14));
  }
}
