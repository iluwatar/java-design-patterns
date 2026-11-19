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
package com.iluwatar.queryobject;

/**
 * The comparison operators a leaf {@link Criterion} can use. Each operator knows both how to
 * compare two values and how it is written in the query language, so the two cannot disagree.
 */
public enum Operator {
  EQUALS("="),
  GREATER_THAN(">"),
  AT_MOST("<=");

  private final String symbol;

  Operator(String symbol) {
    this.symbol = symbol;
  }

  /** The symbol of this operator in the SQL-like query language. */
  public String symbol() {
    return symbol;
  }

  /** Apply this operator to an actual value and the value it is compared with. */
  public <V extends Comparable<? super V>> boolean test(V actual, V expected) {
    var comparison = actual.compareTo(expected);
    return switch (this) {
      case EQUALS -> comparison == 0;
      case GREATER_THAN -> comparison > 0;
      case AT_MOST -> comparison <= 0;
    };
  }
}
