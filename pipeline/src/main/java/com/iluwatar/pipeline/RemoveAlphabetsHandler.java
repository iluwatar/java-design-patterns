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
package com.iluwatar.pipeline;

import java.util.function.IntPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stage handler that returns a new instance of String without the alphabet characters of the input
 * string.
 */
class RemoveAlphabetsHandler implements Handler<String, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoveAlphabetsHandler.class);

  @Override
  public String process(String input) {
    var inputWithoutAlphabets = new StringBuilder();
    var isAlphabetic = (IntPredicate) Character::isAlphabetic;
    input
        .chars()
        .filter(isAlphabetic.negate())
        .mapToObj(x -> (char) x)
        .forEachOrdered(inputWithoutAlphabets::append);

    var inputWithoutAlphabetsStr = inputWithoutAlphabets.toString();
    LOGGER.info(
        String.format(
            "Current handler: %s, input is %s of type %s, output is %s, of type %s",
            RemoveAlphabetsHandler.class,
            input,
            String.class,
            inputWithoutAlphabetsStr,
            String.class));

    return inputWithoutAlphabetsStr;
  }
}
