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
// ABOUTME: A Java beans class that parses HTTP requests and stores client parameters.
// ABOUTME: Used in JSPs to dynamically include elements in the composite view.
package com.iluwatar.compositeview

import jakarta.servlet.http.HttpServletRequest
import java.io.Serializable

/**
 * A Java beans class that parses a http request and stores parameters. Java beans used in JSP's to
 * dynamically include elements in view.
 *
 * @property worldNewsInterest whether current request has world news interest
 * @property sportsInterest whether current request has a sports interest
 * @property businessInterest whether current request has a business interest
 * @property scienceNewsInterest whether current request has a science news interest
 * @property name the client name
 */
class ClientPropertiesBean(
    var worldNewsInterest: Boolean = true,
    var sportsInterest: Boolean = true,
    var businessInterest: Boolean = true,
    var scienceNewsInterest: Boolean = true,
    var name: String = DEFAULT_NAME
) : Serializable {

    /**
     * Constructor that parses an HttpServletRequest and stores all the request parameters.
     *
     * @param req the HttpServletRequest object that is passed in
     */
    constructor(req: HttpServletRequest) : this(
        worldNewsInterest = req.getParameter(WORLD_PARAM).toBoolean(),
        sportsInterest = req.getParameter(SPORTS_PARAM).toBoolean(),
        businessInterest = req.getParameter(BUSINESS_PARAM).toBoolean(),
        scienceNewsInterest = req.getParameter(SCIENCE_PARAM).toBoolean(),
        name = req.getParameter(NAME_PARAM).takeUnless { it.isNullOrEmpty() } ?: DEFAULT_NAME
    )

    companion object {
        private const val WORLD_PARAM = "world"
        private const val SCIENCE_PARAM = "sci"
        private const val SPORTS_PARAM = "sport"
        private const val BUSINESS_PARAM = "bus"
        private const val NAME_PARAM = "name"

        const val DEFAULT_NAME = "DEFAULT_NAME"
    }
}
