/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import static com.iluwatar.saga.orchestration.Saga.Result.CRASHED;
import static com.iluwatar.saga.orchestration.Saga.Result.FINISHED;
import static com.iluwatar.saga.orchestration.Saga.Result.ROLLBACK;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The orchestrator that manages all the transactions and directs
 * the participant services to execute local transactions based on events.
 */
public class SagaOrchestrator {
  private static final Logger LOGGER = LoggerFactory.getLogger(SagaOrchestrator.class);
  private final Saga saga;
  private final ServiceDiscoveryService sd;
  private final CurrentState state;


  /**
   * Create a new service to orchetrate sagas.
   * @param saga saga to process
   * @param sd service discovery @see {@link ServiceDiscoveryService}
   */
  public SagaOrchestrator(Saga saga, ServiceDiscoveryService sd) {
    this.saga = saga;
    this.sd = sd;
    this.state = new CurrentState();
  }

  /**
   * pipeline to execute saga process/story.
   *
   * @param value incoming value
   * @param <K>   type for incoming value
   * @return result @see {@link Saga.Result}
   */
  @SuppressWarnings("unchecked")
  public <K> Saga.Result execute(K value) {
    state.cleanUp();
    LOGGER.info(" The new saga is about to start");
    Saga.Result result = FINISHED;
    K tempVal = value;

    while (true) {
      int next = state.current();
      Saga.Chapter ch = saga.get(next);
      Optional<OrchestrationChapter> srvOpt = sd.find(ch.name);

      if (!srvOpt.isPresent()) {
        state.directionToBack();
        state.back();
        continue;
      }

      OrchestrationChapter srv = srvOpt.get();

      if (state.isForward()) {
        ChapterResult processRes = srv.process(tempVal);
        if (processRes.isSuccess()) {
          next = state.forward();
          tempVal = (K) processRes.getValue();
        } else {
          state.directionToBack();
        }
      } else {
        ChapterResult rlRes = srv.rollback(tempVal);
        if (rlRes.isSuccess()) {
          next = state.back();
          tempVal = (K) rlRes.getValue();
        } else {
          result = CRASHED;
          next = state.back();
        }
      }


      if (!saga.isPresent(next)) {
        return state.isForward() ? FINISHED : result == CRASHED ? CRASHED : ROLLBACK;
      }
    }

  }


  private static class CurrentState {
    int currentNumber;
    boolean isForward;

    void cleanUp() {
      currentNumber = 0;
      isForward = true;
    }

    CurrentState() {
      this.currentNumber = 0;
      this.isForward = true;
    }


    boolean isForward() {
      return isForward;
    }

    void directionToBack() {
      isForward = false;
    }

    int forward() {
      return ++currentNumber;
    }

    int back() {
      return --currentNumber;
    }

    int current() {
      return currentNumber;
    }
  }

}
