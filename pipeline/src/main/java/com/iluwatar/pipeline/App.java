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

import lombok.extern.slf4j.Slf4j;

/**
 * The Pipeline pattern uses ordered stages to process a sequence of input values. Each implemented
 * task is represented by a stage of the pipeline. You can think of pipelines as similar to assembly
 * lines in a factory, where each item in the assembly line is constructed in stages. The partially
 * assembled item is passed from one assembly stage to another. The outputs of the assembly line
 * occur in the same order as that of the inputs.
 *
 * <p>Classes used in this example are suffixed with "Handlers", and synonymously refers to the
 * "stage".
 */
@Slf4j
public class App {
  /**
   * Specify the initial input type for the first stage handler and the expected output type of the
   * last stage handler as type parameters for Pipeline. Use the fluent builder by calling
   * addHandler to add more stage handlers on the pipeline.
   */
  public static void main(String[] args) {
    /*
      Suppose we wanted to pass through a String to a series of filtering stages and convert it
      as a char array on the last stage.

      - Stage handler 1 (pipe): Removing the alphabets, accepts a String input and returns the
      processed String output. This will be used by the next handler as its input.

      - Stage handler 2 (pipe): Removing the digits, accepts a String input and returns the
      processed String output. This shall also be used by the last handler we have.

      - Stage handler 3 (pipe): Converting the String input to a char array handler. We would
      be returning a different type in here since that is what's specified by the requirement.
      This means that at any stages along the pipeline, the handler can return any type of data
      as long as it fulfills the requirements for the next handler's input.

      Suppose we wanted to add another handler after ConvertToCharArrayHandler. That handler
      then is expected to receive an input of char[] array since that is the type being returned
      by the previous handler, ConvertToCharArrayHandler.
     */
    LOGGER.info("Creating pipeline");
    var filters = new Pipeline<>(new RemoveAlphabetsHandler())
        .addHandler(new RemoveDigitsHandler())
        .addHandler(new ConvertToCharArrayHandler());
    var input = "GoYankees123!";
    LOGGER.info("Executing pipeline with input: {}", input);
    var output = filters.execute(input);
    LOGGER.info("Pipeline output: {}", output);
  }
}
