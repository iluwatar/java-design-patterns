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

package com.iluwatar.finite.state.machine;

/**.
 * This model named finite state machine model, which uses finite state machine to
 * implement one thing that pull out all the states, events and actions, and manage
 * the complex state migration logic uniformly.
 * <p></p>
 * In this demo, I create a scenario that there is a game character, who has three states.
 * States: Standing;Walking;Running
 * And also there are two events, which means you can create requests to control it.
 * Events: MOVING_ON;MOVING_OFF
 * In this demo I use state pattern to implement this FSM pattern. For the meaning of every class,
 * refer to the class's Javadoc.
 */
public class App {
  /**.
   * Launcher of this demo.
   * @param args is default for main method.
   */
  public static void main(String[]args) {
    var initState = new StandState();
    var context = new Context(initState);

    context.request(Event.MOVING_ON_OFF);
    context.request(Event.MOVING_RUN);
    context.request(Event.MOVING_ON_OFF);
    context.request(Event.MOVING_ON_OFF);
  }
}
