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
package com.iluwatar.privateclassdata;

/**
 * 
 * The Private Class Data design pattern seeks to reduce exposure of attributes by limiting their
 * visibility. It reduces the number of class attributes by encapsulating them in single data
 * object. It allows the class designer to remove write privilege of attributes that are intended to
 * be set only during construction, even from methods of the target class.
 * <p>
 * In the example we have normal {@link Stew} class with some ingredients given in constructor. Then
 * we have methods to enumerate the ingredients and to taste the stew. The method for tasting the
 * stew alters the private members of the {@link Stew} class.
 * 
 * The problem is solved with the Private Class Data pattern. We introduce {@link ImmutableStew}
 * class that contains {@link StewData}. The private data members of {@link Stew} are now in
 * {@link StewData} and cannot be altered by {@link ImmutableStew} methods.
 *
 */
public class App {

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    // stew is mutable
    Stew stew = new Stew(1, 2, 3, 4);
    stew.mix();
    stew.taste();
    stew.mix();

    // immutable stew protected with Private Class Data pattern
    ImmutableStew immutableStew = new ImmutableStew(2, 4, 3, 6);
    immutableStew.mix();
  }
}
