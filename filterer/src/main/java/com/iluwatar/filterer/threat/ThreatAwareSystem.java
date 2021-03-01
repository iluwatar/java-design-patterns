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

package com.iluwatar.filterer.threat;

import com.iluwatar.filterer.domain.Filterer;

import java.util.List;

/**
 * Represents system that is aware of threats that are present in it.
 */
public interface ThreatAwareSystem {

  /**
   * Returns the system id.
   *
   * @return system id.
   */
  String systemId();

  /**
   * Returns list of threats for this system.
   * @return list of threats for this system.
   */
  List<? extends Threat> threats();

  /**
   * Returns the instance of {@link Filterer} helper interface that allows to covariantly
   * specify lower bound for predicate that we want to filter by.
   * @return an instance of {@link Filterer} helper interface.
   */
  Filterer<? extends ThreatAwareSystem, ? extends Threat> filtered();

}
