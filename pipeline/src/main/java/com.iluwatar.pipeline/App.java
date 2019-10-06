/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

/**
 * The Pipeline pattern uses ordered stages to process a sequence of input values.
 * Each implemented task is represented by a stage of the pipeline. You can think of
 * pipelines as similar to assembly lines in a factory, where each item in the assembly
 * line is constructed in stages. The partially assembled item is passed from one assembly
 * stage to another. The outputs of the assembly line occur in the same order as that of the
 * inputs.
 *
 * Classes used in this example are suffixed with "Handlers", and synonymously refers to the
 * "stage".
 */
public class App {
  /**
   * Specify the initial input type for the first stage handler and the expected output type
   * of the last stage handler as type parameters for Pipeline. Use the fluent builder by
   * calling addHandler to add more stage handlers on the pipeline.
   */
  public static void main(String[] args) {
    Pipeline<String, char[]> filters = new Pipeline<>(new RemoveAlphabetsHandler())
        .addHandler(new RemoveDigitsHandler())
        .addHandler(new ConvertToCharArrayHandler());
  }
}
