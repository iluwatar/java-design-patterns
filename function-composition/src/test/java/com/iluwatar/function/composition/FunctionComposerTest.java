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
package com.iluwatar.function.composition;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.function.Function;

/**
 * Test class for FunctionComposer.
 */
public class FunctionComposerTest {

  /**
   * Tests the composition of two functions.
   */
  @Test
  public void testComposeFunctions() {
    Function<Integer, Integer> timesTwo = x -> x * 2;
    Function<Integer, Integer> square = x -> x * x;

    Function<Integer, Integer> composed = FunctionComposer.composeFunctions(timesTwo, square);

    assertEquals("Expected output of composed functions is 36", 36, (int) composed.apply(3));
  }

  /**
   * Tests function composition with identity function.
   */
  @Test
  public void testComposeWithIdentity() {
    Function<Integer, Integer> identity = Function.identity();
    Function<Integer, Integer> timesThree = x -> x * 3;

    Function<Integer, Integer> composedLeft = FunctionComposer.composeFunctions(identity, timesThree);
    Function<Integer, Integer> composedRight = FunctionComposer.composeFunctions(timesThree, identity);

    assertEquals("Composition with identity on the left should be the same", 9, (int) composedLeft.apply(3));
    assertEquals("Composition with identity on the right should be the same", 9, (int) composedRight.apply(3));
  }

  /**
   * Tests function composition resulting in zero.
   */
  @Test
  public void testComposeToZero() {
    Function<Integer, Integer> multiply = x -> x * 10;
    Function<Integer, Integer> toZero = x -> 0;

    Function<Integer, Integer> composed = FunctionComposer.composeFunctions(multiply, toZero);

    assertEquals("Expected output of function composition leading to zero is 0", 0, (int) composed.apply(5));
  }

  /**
   * Tests the composition with a negative function.
   */
  @Test
  public void testComposeNegative() {
    Function<Integer, Integer> negate = x -> -x;
    Function<Integer, Integer> square = x -> x * x;

    Function<Integer, Integer> composed = FunctionComposer.composeFunctions(negate, square);

    assertEquals("Expected square of negative number to be positive", 9, (int) composed.apply(3));
  }

  /**
   * Tests the composition of functions that cancel each other out.
   */
  @Test
  public void testComposeInverseFunctions() {
    Function<Integer, Integer> timesTwo = x -> x * 2;
    Function<Integer, Integer> half = x -> x / 2;

    Function<Integer, Integer> composed = FunctionComposer.composeFunctions(timesTwo, half);

    assertEquals("Expect the functions to cancel each other out", 5, (int) composed.apply(5));
  }
}
