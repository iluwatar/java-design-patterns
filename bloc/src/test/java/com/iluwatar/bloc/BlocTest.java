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
package com.iluwatar.bloc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlocTest {
  private Bloc bloc;
  private AtomicInteger stateValue;

  @BeforeEach
  void setUp() {
    bloc = new Bloc();
    stateValue = new AtomicInteger(0);
  }

  @Test
  void initialState() {
    assertTrue(bloc.getListeners().isEmpty(), "No listeners should be present initially.");
  }

  @Test
  void IncrementUpdateState() {
    bloc.addListener(state -> stateValue.set(state.value()));
    bloc.increment();
    assertEquals(1, stateValue.get(), "State should increment to 1");
  }

  @Test
  void DecrementUpdateState() {
    bloc.addListener(state -> stateValue.set(state.value()));
    bloc.decrement();
    assertEquals(-1, stateValue.get(), "State should decrement to -1");
  }

  @Test
  void addingListener() {
    bloc.addListener(state -> {});
    assertEquals(1, bloc.getListeners().size(), "Listener count should be 1.");
  }

  @Test
  void removingListener() {
    StateListener<State> listener = state -> {};
    bloc.addListener(listener);
    bloc.removeListener(listener);
    assertTrue(bloc.getListeners().isEmpty(), "Listener count should be 0 after removal.");
  }

  @Test
  void multipleListeners() {
    AtomicInteger secondValue = new AtomicInteger();
    bloc.addListener(state -> stateValue.set(state.value()));
    bloc.addListener(state -> secondValue.set(state.value()));
    bloc.increment();
    assertEquals(1, stateValue.get(), "First listener should receive state 1.");
    assertEquals(1, secondValue.get(), "Second listener should receive state 1.");
  }
}
