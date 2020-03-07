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

package com.iluwatar.saga.choreography;


/**
 * ChoreographyChapter is an interface representing a contract for an external service. In that
 * case, a service needs to make a decision what to do further hence the server needs to get all
 * context representing {@link Saga}
 */
public interface ChoreographyChapter {

  /**
   * In that case, every method is responsible to make a decision on what to do then.
   *
   * @param saga incoming saga
   * @return saga result
   */
  Saga execute(Saga saga);

  /**
   * get name method.
   *
   * @return service name.
   */
  String getName();

  /**
   * The operation executed in general case.
   *
   * @param saga incoming saga
   * @return result {@link Saga}
   */
  Saga process(Saga saga);

  /**
   * The operation executed in rollback case.
   *
   * @param saga incoming saga
   * @return result {@link Saga}
   */
  Saga rollback(Saga saga);


}
