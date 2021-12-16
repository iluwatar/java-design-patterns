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

package com.iluwatar.compositeentity;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;


/**
 * Composite entity is a Java EE Software design pattern and it is used to model, represent, and
 * manage a set of interrelated persistent objects rather than representing them as individual
 * fine-grained entity beans, and also a composite entity bean represents a graph of objects.
 */
@Slf4j
public class App {


  /**
   * An instance that a console manages two related objects.
   */
  public App(String message, String signal) {
    var console = new CompositeEntity();
    console.init();
    console.setData(message, signal);
    Arrays.stream(console.getData()).forEach(LOGGER::info);
    console.setData("Danger", "Red Light");
    Arrays.stream(console.getData()).forEach(LOGGER::info);
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    new App("No Danger", "Green Light");

  }
}
