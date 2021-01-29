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

package com.iluwatar.leaderelection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Abstract class of all the message manager classes.
 */
public abstract class AbstractMessageManager implements MessageManager {

  /**
   * Contain all the instances in the system. Key is its ID, and value is the instance itself.
   */
  protected Map<Integer, Instance> instanceMap;

  /**
   * Construtor of AbstractMessageManager.
   */
  public AbstractMessageManager(Map<Integer, Instance> instanceMap) {
    this.instanceMap = instanceMap;
  }

  /**
   * Find the next instance with smallest ID.
   *
   * @return The next instance.
   */
  protected Instance findNextInstance(int currentId) {
    Instance result = null;
    var candidateList = instanceMap.keySet()
        .stream()
        .filter((i) -> i > currentId && instanceMap.get(i).isAlive())
        .sorted()
        .collect(Collectors.toList());
    if (candidateList.isEmpty()) {
      var index = instanceMap.keySet()
          .stream()
          .filter((i) -> instanceMap.get(i).isAlive())
          .sorted()
          .collect(Collectors.toList())
          .get(0);
      result = instanceMap.get(index);
    } else {
      var index = candidateList.get(0);
      result = instanceMap.get(index);
    }
    return result;
  }

}
