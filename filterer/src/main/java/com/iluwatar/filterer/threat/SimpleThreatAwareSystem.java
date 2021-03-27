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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * {@inheritDoc}
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class SimpleThreatAwareSystem implements ThreatAwareSystem {

  private final String systemId;
  private final List<Threat> issues;

  /**
   * {@inheritDoc}
   */
  @Override
  public String systemId() {
    return systemId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends Threat> threats() {
    return new ArrayList<>(issues);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Filterer<? extends ThreatAwareSystem, ? extends Threat> filtered() {
    return this::filteredGroup;
  }

  private ThreatAwareSystem filteredGroup(Predicate<? super Threat> predicate) {
    return new SimpleThreatAwareSystem(this.systemId, filteredItems(predicate));
  }

  private List<Threat> filteredItems(Predicate<? super Threat> predicate) {
    return this.issues.stream()
        .filter(predicate)
        .collect(Collectors.toUnmodifiableList());
  }

}
