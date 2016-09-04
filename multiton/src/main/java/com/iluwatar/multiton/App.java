/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.multiton;

/**
 * 
 * Whereas Singleton design pattern introduces single globally accessible object the Multiton
 * pattern defines many globally accessible objects. The client asks for the correct instance from
 * the Multiton by passing an enumeration as parameter.
 * <p>
 * In this example {@link Nazgul} is the Multiton and we can ask single {@link Nazgul} from it using
 * {@link NazgulName}. The {@link Nazgul}s are statically initialized and stored in concurrent hash
 * map.
 *
 */
public class App {

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    System.out.println("KHAMUL=" + Nazgul.getInstance(NazgulName.KHAMUL));
    System.out.println("MURAZOR=" + Nazgul.getInstance(NazgulName.MURAZOR));
    System.out.println("DWAR=" + Nazgul.getInstance(NazgulName.DWAR));
    System.out.println("JI_INDUR=" + Nazgul.getInstance(NazgulName.JI_INDUR));
    System.out.println("AKHORAHIL=" + Nazgul.getInstance(NazgulName.AKHORAHIL));
    System.out.println("HOARMURATH=" + Nazgul.getInstance(NazgulName.HOARMURATH));
    System.out.println("ADUNAPHEL=" + Nazgul.getInstance(NazgulName.ADUNAPHEL));
    System.out.println("REN=" + Nazgul.getInstance(NazgulName.REN));
    System.out.println("UVATHA=" + Nazgul.getInstance(NazgulName.UVATHA));
  }
}
