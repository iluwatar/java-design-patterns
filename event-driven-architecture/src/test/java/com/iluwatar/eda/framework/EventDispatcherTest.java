/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.eda.framework;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.event.UserUpdatedEvent;
import com.iluwatar.eda.handler.UserCreatedEventHandler;
import com.iluwatar.eda.handler.UserUpdatedEventHandler;
import com.iluwatar.eda.model.User;
import org.junit.jupiter.api.Test;

/**
 * Event Dispatcher unit tests to assert and verify correct event dispatcher behaviour
 */
class EventDispatcherTest {

  /**
   * This unit test should register events and event handlers correctly with the event dispatcher
   * and events should be dispatched accordingly.
   */
  @Test
  void testEventDriverPattern() {

    var dispatcher = spy(new EventDispatcher());
    var userCreatedEventHandler = spy(new UserCreatedEventHandler());
    var userUpdatedEventHandler = spy(new UserUpdatedEventHandler());
    dispatcher.registerHandler(UserCreatedEvent.class, userCreatedEventHandler);
    dispatcher.registerHandler(UserUpdatedEvent.class, userUpdatedEventHandler);

    var user = new User("iluwatar");

    var userCreatedEvent = new UserCreatedEvent(user);
    var userUpdatedEvent = new UserUpdatedEvent(user);

    //fire a userCreatedEvent and verify that userCreatedEventHandler has been invoked.
    dispatcher.dispatch(userCreatedEvent);
    verify(userCreatedEventHandler).onEvent(userCreatedEvent);
    verify(dispatcher).dispatch(userCreatedEvent);

    //fire a userCreatedEvent and verify that userUpdatedEventHandler has been invoked.
    dispatcher.dispatch(userUpdatedEvent);
    verify(userUpdatedEventHandler).onEvent(userUpdatedEvent);
    verify(dispatcher).dispatch(userUpdatedEvent);
  }

}
