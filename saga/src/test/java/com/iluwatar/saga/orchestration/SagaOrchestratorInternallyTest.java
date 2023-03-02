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
package com.iluwatar.saga.orchestration;

import org.junit.jupiter.api.Test;

import static com.iluwatar.saga.orchestration.Saga.Result;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * test to test orchestration logic
 */
class SagaOrchestratorInternallyTest {

  private final List<String> records = new ArrayList<>();

  @Test
  void executeTest() {
    var sagaOrchestrator = new SagaOrchestrator(newSaga(), serviceDiscovery());
    var result = sagaOrchestrator.execute(1);
    assertEquals(Result.ROLLBACK, result);
    assertArrayEquals(
            new String[]{"+1", "+2", "+3", "+4", "-4", "-3", "-2", "-1"},
            records.toArray(new String[]{}));
  }

  private static Saga newSaga() {
    return Saga.create()
        .chapter("1")
        .chapter("2")
        .chapter("3")
        .chapter("4");
  }

  private ServiceDiscoveryService serviceDiscovery() {
    return new ServiceDiscoveryService()
        .discover(new Service1())
        .discover(new Service2())
        .discover(new Service3())
        .discover(new Service4());
  }

  class Service1 extends Service<Integer> {

    @Override
    public String getName() {
      return "1";
    }

    @Override
    public ChapterResult<Integer> process(Integer value) {
      records.add("+1");
      return ChapterResult.success(value);
    }

    @Override
    public ChapterResult<Integer> rollback(Integer value) {
      records.add("-1");
      return ChapterResult.success(value);
    }
  }

  class Service2 extends Service<Integer> {

    @Override
    public String getName() {
      return "2";
    }

    @Override
    public ChapterResult<Integer> process(Integer value) {
      records.add("+2");
      return ChapterResult.success(value);
    }

    @Override
    public ChapterResult<Integer> rollback(Integer value) {
      records.add("-2");
      return ChapterResult.success(value);
    }
  }

  class Service3 extends Service<Integer> {

    @Override
    public String getName() {
      return "3";
    }

    @Override
    public ChapterResult<Integer> process(Integer value) {
      records.add("+3");
      return ChapterResult.success(value);
    }

    @Override
    public ChapterResult<Integer> rollback(Integer value) {
      records.add("-3");
      return ChapterResult.success(value);
    }
  }

  class Service4 extends Service<Integer> {

    @Override
    public String getName() {
      return "4";
    }

    @Override
    public ChapterResult<Integer> process(Integer value) {
      records.add("+4");
      return ChapterResult.failure(value);
    }

    @Override
    public ChapterResult<Integer> rollback(Integer value) {
      records.add("-4");
      return ChapterResult.success(value);
    }
  }
}