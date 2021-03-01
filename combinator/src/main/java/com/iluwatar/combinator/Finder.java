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

package com.iluwatar.combinator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Functional interface to find lines in text.
 */
public interface Finder {

  /**
   * The function to find lines in text.
   * @param text full tet
   * @return result of searching
   */
  List<String> find(String text);

  /**
   * Simple implementation of function {@link #find(String)}.
   * @param word for searching
   * @return this
   */
  static Finder contains(String word) {
    return txt -> Stream.of(txt.split("\n"))
        .filter(line -> line.toLowerCase().contains(word.toLowerCase()))
        .collect(Collectors.toList());
  }

  /**
   * combinator not.
   * @param notFinder finder to combine
   * @return new finder including previous finders
   */
  default Finder not(Finder notFinder) {
    return txt -> {
      List<String> res = this.find(txt);
      res.removeAll(notFinder.find(txt));
      return res;
    };
  }

  /**
   * combinator or.
   * @param orFinder finder to combine
   * @return new finder including previous finders
   */
  default Finder or(Finder orFinder) {
    return txt -> {
      List<String> res = this.find(txt);
      res.addAll(orFinder.find(txt));
      return res;
    };
  }

  /**
   * combinator or.
   * @param andFinder finder to combine
   * @return new finder including previous finders
   */
  default Finder and(Finder andFinder) {
    return
        txt -> this
            .find(txt)
            .stream()
            .flatMap(line -> andFinder.find(line).stream())
            .collect(Collectors.toList());
  }

}
