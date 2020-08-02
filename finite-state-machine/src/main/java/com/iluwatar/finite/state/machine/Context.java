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
 * This class is a context for the State.
 * It defines the State that we focus on.
 */
public class Context {
  private State state;

  /**.
   * Constructor for Context.
   * @param state is state data.
   */
  public Context(State state) {
    setState(state);
  }

  /**.
   * getter for state.
   * @return state of this context.
   */
  public State getState() {
    return state;
  }

  /**.
   * setter for state.
   * @param state is a state data
   */
  public void setState(State state) {
    this.state = state;
  }

  /**.
   * request method for an event, then it will call handle method.
   * @param event is an event.
   */
  public void request(Event event) {
    state.handle(this,event);
  }
}
