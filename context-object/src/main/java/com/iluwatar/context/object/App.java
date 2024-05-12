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
package com.iluwatar.context.object;

import lombok.extern.slf4j.Slf4j;

/**
 * In the context object pattern, information and data from underlying protocol-specific classes/systems is decoupled
 * and stored into a protocol-independent object in an organised format. The pattern ensures the data contained within
 * the context object can be shared and further structured between different layers of a software system.
 *
 * <p> In this example we show how a context object {@link ServiceContext} can be initiated, edited and passed/retrieved
 * in different layers of the program ({@link LayerA}, {@link LayerB}, {@link LayerC}) through use of static methods. </p>
 */
@Slf4j
public class App {

  private static final String SERVICE = "SERVICE";

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    //Initiate first layer and add service information into context
    var layerA = new LayerA();
    layerA.addAccountInfo(SERVICE);

    logContext(layerA.getContext());

    //Initiate second layer and preserving information retrieved in first layer through passing context object
    var layerB = new LayerB(layerA);
    layerB.addSessionInfo(SERVICE);

    logContext(layerB.getContext());

    //Initiate third layer and preserving information retrieved in first and second layer through passing context object
    var layerC = new LayerC(layerB);
    layerC.addSearchInfo(SERVICE);

    logContext(layerC.getContext());
  }

  private static void logContext(ServiceContext context) {
    LOGGER.info("Context = {}", context);

  }
}