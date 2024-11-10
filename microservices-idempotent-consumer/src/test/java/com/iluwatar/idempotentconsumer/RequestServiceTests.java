/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.idempotentconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RequestServiceTests {
  private RequestService requestService;
  @Mock
  private RequestRepository requestRepository;
  private RequestStateMachine requestStateMachine;
  @BeforeEach
  void setUp() {
    requestStateMachine = new RequestStateMachine();
    requestService = new RequestService(requestRepository, requestStateMachine);
  }

  @Test
  void createRequest_whenNotExists() {
    UUID uuid = UUID.randomUUID();
    Request request = new Request(uuid);
    when(requestRepository.findById(any())).thenReturn(Optional.empty());
    when(requestRepository.save(request)).thenReturn(request);
    assertEquals(request, requestService.create(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(1)).save(any());
  }
  @Test
  void createRequest_whenExists() {
    UUID uuid = UUID.randomUUID();
    Request request = new Request(uuid);
    when(requestRepository.findById(any())).thenReturn(Optional.of(request));
    assertEquals(request, requestService.create(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(0)).save(any());
  }

  @Test
  void startRequest_whenNotExists_shouldThrowError() {
    UUID uuid = UUID.randomUUID();
    when(requestRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(RequestNotFoundException.class, ()->requestService.start(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(0)).save(any());
  }

  @Test
  void startRequest_whenIsPending() {
    UUID uuid = UUID.randomUUID();
    Request request = new Request(uuid);
    Request startedEntity = new Request(uuid, Request.Status.STARTED);
    when(requestRepository.findById(any())).thenReturn(Optional.of(request));
    when(requestRepository.save(any())).thenReturn(startedEntity);
    assertEquals(startedEntity, requestService.start(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(1)).save(startedEntity);
  }

  @Test
  void startRequest_whenIsStarted_shouldThrowError() {
    UUID uuid = UUID.randomUUID();
    Request requestStarted = new Request(uuid, Request.Status.STARTED);
    when(requestRepository.findById(any())).thenReturn(Optional.of(requestStarted));
    assertThrows(InvalidNextStateException.class, ()->requestService.start(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(0)).save(any());
  }

  @Test
  void startRequest_whenIsCompleted_shouldThrowError() {
    UUID uuid = UUID.randomUUID();
    Request requestStarted = new Request(uuid, Request.Status.COMPLETED);
    when(requestRepository.findById(any())).thenReturn(Optional.of(requestStarted));
    assertThrows(InvalidNextStateException.class, ()->requestService.start(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(0)).save(any());
  }

  @Test
  void completeRequest_whenStarted() {
    UUID uuid = UUID.randomUUID();
    Request request = new Request(uuid, Request.Status.STARTED);
    Request completedEntity = new Request(uuid, Request.Status.COMPLETED);
    when(requestRepository.findById(any())).thenReturn(Optional.of(request));
    when(requestRepository.save(any())).thenReturn(completedEntity);
    assertEquals(completedEntity, requestService.complete(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(1)).save(completedEntity);
  }
  @Test
  void completeRequest_whenNotInprogress() {
    UUID uuid = UUID.randomUUID();
    Request request = new Request(uuid);
    when(requestRepository.findById(any())).thenReturn(Optional.of(request));
    assertThrows(InvalidNextStateException.class, () -> requestService.complete(uuid));
    verify(requestRepository, times(1)).findById(uuid);
    verify(requestRepository, times(0)).save(any());
  }
}