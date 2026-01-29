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
// ABOUTME: Implementation of ProductInformationClient that communicates with the information micro-service.
// ABOUTME: Uses Java HTTP client to fetch product title from the information service endpoint.
package com.iluwatar.aggregator.microservices

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val logger = KotlinLogging.logger {}

/**
 * An adapter to communicate with information micro-service.
 */
@Component
class ProductInformationClientImpl : ProductInformationClient {
    override fun getProductTitle(): String? {
        val request =
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:51515/information"))
                .build()
        val client = HttpClient.newHttpClient()
        return try {
            val httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString())
            httpResponse.body()
        } catch (ioe: IOException) {
            logger.error(ioe) { "IOException Occurred" }
            null
        } catch (ie: InterruptedException) {
            logger.error(ie) { "InterruptedException Occurred" }
            Thread.currentThread().interrupt()
            null
        }
    }
}
