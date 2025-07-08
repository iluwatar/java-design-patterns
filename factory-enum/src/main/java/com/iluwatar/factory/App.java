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
package com.iluwatar.factory;

import lombok.extern.slf4j.Slf4j;

/**
 * The Factory Enum Pattern is a creational design pattern that leverages Java enums to encapsulate
 * the creation logic of different object types. It promotes cleaner code by removing the need for
 * separate factory classes and centralizing instantiation within type-safe enum constants.
 *
 * <p>In this example, different file processors (Excel, PDF) implement the same interface. The
 * FileProcessorType enum holds the corresponding instance for each processor, allowing the client
 * code to retrieve them in a clean and consistent way.
 */
@Slf4j
public class App {

  /** Program main entry point. */
  public static void main(String[] args) {
    LOGGER.info("The alchemist begins his work.");
    FileProcessor excelProcessor = FileProcessorType.EXCEL.getInstance();
    FileProcessor pdfProcessor = FileProcessorType.PDF.getInstance();
    LOGGER.info(excelProcessor.getDescription());
    LOGGER.info(pdfProcessor.getDescription());
  }
}
