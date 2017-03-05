/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.databus;

import com.iluwatar.databus.data.StoppingData;
import com.iluwatar.databus.data.StartingData;
import com.iluwatar.databus.data.MessageData;
import com.iluwatar.databus.members.CounterMember;
import com.iluwatar.databus.members.StatusMember;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * The Data Bus pattern
 * <p>
 * <p>{@see http://wiki.c2.com/?DataBusPattern}</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Slf4j
class App {

  public static void main(String[] args) {
    final DataBus bus = DataBus.getInstance();
    bus.subscribe(new StatusMember(1));
    bus.subscribe(new StatusMember(2));
    final CounterMember foo = new CounterMember("Foo");
    final CounterMember bar = new CounterMember("Bar");
    bus.subscribe(foo);
    bus.publish(StartingData.of(LocalDateTime.now()));
    bus.publish(MessageData.of("Only Foo should see this"));
    bus.subscribe(bar);
    bus.publish(MessageData.of("Foo and Bar should see this"));
    bus.unsubscribe(foo);
    bus.publish(MessageData.of("Only Bar should see this"));
    bus.publish(StoppingData.of(LocalDateTime.now()));
  }
}
