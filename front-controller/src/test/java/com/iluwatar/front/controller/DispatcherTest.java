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
package com.iluwatar.front.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DispatcherTest {

  private Dispatcher dispatcher;

  @BeforeEach
  public void setUp() {
    dispatcher = new Dispatcher();
  }

  @Test
  void testDispatchKnownCommand() {
    Command mockCommand = mock(ArcherCommand.class);
    dispatcher = spy(dispatcher);
    doReturn(mockCommand).when(dispatcher).getCommand("Archer");

    dispatcher.dispatch("Archer");

    verify(mockCommand, times(1)).process();
  }

  @Test
  void testDispatchUnknownCommand() {
    Command mockCommand = mock(UnknownCommand.class);
    dispatcher = spy(dispatcher);
    doReturn(mockCommand).when(dispatcher).getCommand("Unknown");

    dispatcher.dispatch("Unknown");

    verify(mockCommand, times(1)).process();
  }

  @Test
  void testGetCommandKnown() {
    Command command = dispatcher.getCommand("Archer");
    assertNotNull(command);
    assertTrue(command instanceof ArcherCommand);
  }

  @Test
  void testGetCommandUnknown() {
    Command command = dispatcher.getCommand("Unknown");
    assertNotNull(command);
    assertTrue(command instanceof UnknownCommand);
  }

  @Test
  void testGetCommandClassKnown() {
    Class<?> commandClass = Dispatcher.getCommandClass("Archer");
    assertNotNull(commandClass);
    assertEquals(ArcherCommand.class, commandClass);
  }

  @Test
  void testGetCommandClassUnknown() {
    Class<?> commandClass = Dispatcher.getCommandClass("Unknown");
    assertNotNull(commandClass);
    assertEquals(UnknownCommand.class, commandClass);
  }
}
