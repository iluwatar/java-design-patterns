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

package com.iluwatar.parameter.object;

public class SearchService {

  /* Parameter Object example. Default values are abstracted into the Parameter Object
  at the time of Object creation */
  public String search(ParameterObject parameterObject) {
    return getQuerySummary(parameterObject.getType(), parameterObject.getSortBy(),
        parameterObject.getSortOrder());
  }

  //Method Overloading example. SortOrder is defaulted in this method
  public String search(String type, String sortBy) {
    return getQuerySummary(type, sortBy, SortOrder.ASC);
  }

  /* Method Overloading example. SortBy is defaulted in this method. Note that the type has to be
  different here to overload the method */
  public String search(String type, SortOrder sortOrder) {
    return getQuerySummary(type, "price", sortOrder);
  }

  private String getQuerySummary(String type, String sortBy, SortOrder sortOrder) {
    return "Requesting shoes of type \"" + type + "\" sorted by \"" + sortBy + "\" in \""
        + sortOrder.getValue() + "ending\" order...";
  }
}
