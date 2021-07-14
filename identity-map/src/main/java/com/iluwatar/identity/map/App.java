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

package com.iluwatar.identity.map;

import lombok.extern.slf4j.Slf4j;

/**
 * the App class.
 */
@Slf4j
public final class App {
  /**
   * the main method.
   *
   * @param args usually doesn't need arguments.
   */
  public static void main(final String[] args) {
    StaffDataBase staffDataBase = new StaffDataBase();
    Staff staff = staffDataBase.finder("s1");
    Staff staff1 = staffDataBase.finder("s1");
    Staff staff2 = staffDataBase.finder("s2");
    LOGGER.info(staff.toString());
    LOGGER.info(staff1.toString());
    LOGGER.info(staff2.toString());

  }

  /**
   * the hidden constructor.
   */
  private App() {

  }
}
