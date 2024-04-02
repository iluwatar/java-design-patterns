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
package com.iluwatar;

import java.time.LocalDate;

/**
 * The notification pattern captures information passed between layers, validates the information, and returns
 * any errors to the presentation layer if needed.
 *
 * <p>In this code, this pattern is implemented through the example of a form being submitted to register
 * a worker. The worker inputs their name, occupation, and date of birth to the RegisterWorkerForm (which acts
 * as our presentation layer), and passes it to the RegisterWorker class (our domain layer) which validates it.
 * Any errors caught by the domain layer are then passed back to the presentation layer through the
 * RegisterWorkerDto.</p>
 */
public class App {

  private static final String NAME = "";
  private static final String OCCUPATION = "";
  private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2016, 7, 13);

  public static void main(String[] args) {
    var form = new RegisterWorkerForm(NAME, OCCUPATION, DATE_OF_BIRTH);
    form.submit();
  }

}
