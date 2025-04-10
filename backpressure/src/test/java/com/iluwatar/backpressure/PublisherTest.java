package com.iluwatar.backpressure;

import static com.iluwatar.backpressure.Publisher.publish;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class PublisherTest {

  @Test
  public void testPublish() {

    Flux<Integer> flux = publish(1, 3, 200);

    StepVerifier.withVirtualTime(() -> flux)
        .expectSubscription()
        .expectNoEvent(Duration.ofMillis(200))
        .expectNext(1)
        .expectNoEvent(Duration.ofSeconds(200))
        .expectNext(2)
        .expectNoEvent(Duration.ofSeconds(200))
        .expectNext(3)
        .verifyComplete();
  }
}
