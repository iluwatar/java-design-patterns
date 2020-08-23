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

package com.iluwatar.filterer.threat;

import java.util.Objects;

/**
 * Represents a simple threat.
 */
public class SimpleThreat implements Threat {

  private final ThreatType threatType;
  private final int id;
  private final String name;

  /**
   * Constructor.
   *
   * @param threatType {@link ThreatType}.
   * @param id         threat id.
   * @param name       threat name.
   */
  public SimpleThreat(final ThreatType threatType, final int id, String name) {
    this.threatType = threatType;
    this.id = id;
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int id() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ThreatType type() {
    return threatType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var that = (SimpleThreat) o;
    return id == that.id
            && threatType == that.threatType
            && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(threatType, id, name);
  }

  @Override
  public String toString() {
    return "SimpleThreat{"
            + "threatType=" + threatType
            + ", id=" + id
            + ", name='" + name + '\''
            + '}';
  }
}
