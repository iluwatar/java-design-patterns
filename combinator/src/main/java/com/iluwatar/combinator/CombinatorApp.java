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
package com.iluwatar.combinator;

import lombok.extern.slf4j.Slf4j;


/**
 * The functional pattern representing a style of organizing libraries
 * centered around the idea of combining functions.
 * Putting it simply, there is some type T, some functions
 * for constructing "primitive" values of type T,
 * and some "combinators" which can combine values of type T
 * in various ways to build up more complex values of type T.
 * The class {@link Finder} defines a simple function {@link Finder#find(String)}
 * and connected functions
 * {@link Finder#or(Finder)},
 * {@link Finder#not(Finder)},
 * {@link Finder#and(Finder)}
 * Using them the became possible to get more complex functions {@link Finders}
 */
@Slf4j
public class CombinatorApp {

  private static final String TEXT = """
            It was many and many a year ago,
            In a kingdom by the sea,
            That a maiden there lived whom you may know
            By the name of ANNABEL LEE;
            And this maiden she lived with no other thought
            Than to love and be loved by me.
            I was a child and she was a child,
            In this kingdom by the sea;
            But we loved with a love that was more than love-
            I and my Annabel Lee;
            With a love that the winged seraphs of heaven
            Coveted her and me.""";

  /**
   * main.
   * @param args args
   */
  public static void main(String[] args) {
    var queriesOr = new String[]{"many", "Annabel"};
    var finder = Finders.expandedFinder(queriesOr);
    var res = finder.find(text());
    LOGGER.info("the result of expanded(or) query[{}] is {}", queriesOr, res);

    var queriesAnd = new String[]{"Annabel", "my"};
    finder = Finders.specializedFinder(queriesAnd);
    res = finder.find(text());
    LOGGER.info("the result of specialized(and) query[{}] is {}", queriesAnd, res);

    finder = Finders.advancedFinder("it was", "kingdom", "sea");
    res = finder.find(text());
    LOGGER.info("the result of advanced query is {}", res);

    res = Finders.filteredFinder(" was ", "many", "child").find(text());
    LOGGER.info("the result of filtered query is {}", res);

  }

  private static String text() {

    return TEXT;
  }

}
