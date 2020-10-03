/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import lombok.extern.slf4j.Slf4j;

/**
 * Whereas Singleton design pattern introduces single globally accessible object the Multiton
 * pattern defines many globally accessible objects. The client asks for the correct instance from
 * the Multiton by passing an enumeration as parameter.
 *
 * <p>There is more than one way to implement the multiton design pattern. In the first example
 * {@link Nazgul} is the Multiton and we can ask single {@link Nazgul} from it using {@link
 * NazgulName}. The {@link Nazgul}s are statically initialized and stored in concurrent hash map.
 *
 * <p>In the enum implementation {@link NazgulEnum} is the multiton. It is static and mutable
 * because of the way java supports enums.
 */
@Slf4j
public class App {


  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    // eagerly initialized multiton
    log.info("KHAMUL={}", Nazgul.getInstance(NazgulName.KHAMUL));
    log.info("MURAZOR={}", Nazgul.getInstance(NazgulName.MURAZOR));
    log.info("DWAR={}", Nazgul.getInstance(NazgulName.DWAR));
    log.info("JI_INDUR={}", Nazgul.getInstance(NazgulName.JI_INDUR));
    log.info("AKHORAHIL={}", Nazgul.getInstance(NazgulName.AKHORAHIL));
    log.info("HOARMURATH={}", Nazgul.getInstance(NazgulName.HOARMURATH));
    log.info("ADUNAPHEL={}", Nazgul.getInstance(NazgulName.ADUNAPHEL));
    log.info("REN={}", Nazgul.getInstance(NazgulName.REN));
    log.info("UVATHA={}", Nazgul.getInstance(NazgulName.UVATHA));

    // enum multiton
    log.info("KHAMUL={}", NazgulEnum.KHAMUL);
    log.info("MURAZOR={}", NazgulEnum.MURAZOR);
    log.info("DWAR={}", NazgulEnum.DWAR);
    log.info("JI_INDUR={}", NazgulEnum.JI_INDUR);
    log.info("AKHORAHIL={}", NazgulEnum.AKHORAHIL);
    log.info("HOARMURATH={}", NazgulEnum.HOARMURATH);
    log.info("ADUNAPHEL={}", NazgulEnum.ADUNAPHEL);
    log.info("REN={}", NazgulEnum.REN);
    log.info("UVATHA={}", NazgulEnum.UVATHA);
  }
}
