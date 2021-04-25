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

package com.iluwatar.identityfield;

import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


/**
 * The type App.
 */
@Slf4j
public class App {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   *
   * Take the ID in the person class as the identity field,
   * initialize the person and store it in the linked list,
   * and then access the ID through the getid method
   */
  public static void main(String[] args) {
    List<Person> pearsonList = new LinkedList<>();

    for (var i = 0; i < 5; i++) {
      var person = new Person(i, "pearson");
      String t = "pearson" + Integer.toString(i);
      person.setName(t);
      pearsonList.add(person);
    }

    for (var i = 0; i < 5; i++) {
      LOGGER.info(pearsonList.get(i).getId() + "");
      LOGGER.info(pearsonList.get(i).getName());
    }
  }
}
