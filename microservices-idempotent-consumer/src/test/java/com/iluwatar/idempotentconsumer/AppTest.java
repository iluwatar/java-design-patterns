package com.iluwatar.idempotentconsumer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Application test
 */
class AppTest {

  @Test
  void main() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }

  @Test
  void run() throws Exception {
    RequestService requestService = Mockito.mock(RequestService.class);
    RequestRepository requestRepository = Mockito.mock(RequestRepository.class);
    UUID uuid = UUID.randomUUID();
    Request requestPending = new Request(uuid);
    Request requestStarted = new Request(uuid, Request.Status.STARTED);
    Request requestCompleted = new Request(uuid, Request.Status.COMPLETED);
    when(requestService.create(any())).thenReturn(requestPending);
    when(requestService.start(any())).thenReturn(requestStarted);
    when(requestService.complete(any())).thenReturn(requestCompleted);

    CommandLineRunner runner = new App().run(requestService, requestRepository);

    runner.run();

    verify(requestService, times(3)).create(any());
    verify(requestService, times(2)).start(any());
    verify(requestService, times(1)).complete(any());
    verify(requestRepository, times(1)).count();
  }
}