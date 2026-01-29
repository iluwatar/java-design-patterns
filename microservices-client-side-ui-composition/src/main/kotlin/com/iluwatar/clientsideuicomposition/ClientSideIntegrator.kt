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
// ABOUTME: ClientSideIntegrator assembles frontend components into a cohesive user interface.
// ABOUTME: Uses ApiGateway to route requests and compose UI fragments dynamically.
package com.iluwatar.clientsideuicomposition

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * ClientSideIntegrator class simulates the client-side integration layer that dynamically assembles
 * various frontend components into a cohesive user interface.
 */
class ClientSideIntegrator(private val apiGateway: ApiGateway) {

    /**
     * Composes the user interface dynamically by fetching data from different frontend components
     * based on provided parameters.
     *
     * @param path the route of the frontend component
     * @param params a map of dynamic parameters to influence the data fetching
     */
    fun composeUi(path: String, params: Map<String, String>) {
        val data = apiGateway.handleRequest(path, params)
        logger.info { "Composed UI Component for path '$path':" }
        logger.info { data }
    }
}
