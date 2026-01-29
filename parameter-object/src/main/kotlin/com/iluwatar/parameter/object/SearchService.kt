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
package com.iluwatar.parameter.`object`

// ABOUTME: Service demonstrating how the parameter object pattern reduces method overloading.
// ABOUTME: Provides overloaded search methods alongside a single search(ParameterObject) method.

/** SearchService to demonstrate parameter object pattern. */
class SearchService {

    /**
     * Below two methods of name `search` are overloaded so that we can send a default value for one of
     * the criteria and call the final api. A default SortOrder is sent in the first method and a
     * default SortBy is sent in the second method. So two separate method definitions are needed for
     * having default values for one argument in each case. Hence, multiple overloaded methods are
     * needed as the number of arguments increases.
     */
    fun search(type: String, sortBy: String): String =
        getQuerySummary(type, sortBy, SortOrder.ASC)

    fun search(type: String, sortOrder: SortOrder): String =
        getQuerySummary(type, "price", sortOrder)

    /**
     * The need for multiple method definitions can be avoided by the Parameter Object pattern. Below
     * is the example where only one method is required and all the logic for having default values
     * are abstracted into the Parameter Object at the time of object creation.
     */
    fun search(parameterObject: ParameterObject): String =
        getQuerySummary(parameterObject.type, parameterObject.sortBy, parameterObject.sortOrder)

    private fun getQuerySummary(type: String, sortBy: String, sortOrder: SortOrder): String =
        "Requesting shoes of type \"$type\" sorted by \"$sortBy\" in \"${sortOrder.value}ending\" order.."
}
