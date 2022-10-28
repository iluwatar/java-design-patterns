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

package com.iluwatar.parameter.object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchServiceTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceTest.class);
  private ParameterObject parameterObject;
  private SearchService searchService;

  @BeforeEach
  public void setUp() {
    //Creating parameter object with default values set
    parameterObject = ParameterObject.newBuilder()
        .withType("sneakers")
        .build();

    searchService = new SearchService();
  }

  /**
   *  Testing parameter object against the overloaded method to verify if the behaviour is same.
   */
  @Test
  public void testDefaultParametersMatch() {
    assertEquals(searchService.search(parameterObject), searchService.search("sneakers",
        SortOrder.ASC), "Default Parameter values do not not match.");
    LOGGER.info("SortBy Default parameter value matches.");

    assertEquals(searchService.search(parameterObject), searchService.search("sneakers",
        "price"), "Default Parameter values do not not match.");
    LOGGER.info("SortOrder Default parameter value matches.");

    LOGGER.info("testDefaultParametersMatch executed successfully without errors.");
  }
}
