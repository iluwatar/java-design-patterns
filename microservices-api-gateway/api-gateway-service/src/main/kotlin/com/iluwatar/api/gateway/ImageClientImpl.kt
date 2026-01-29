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
// ABOUTME: HTTP client adapter for communicating with the Image microservice.
// ABOUTME: Makes GET requests to retrieve image paths from the image service.
package com.iluwatar.api.gateway

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers

private val logger = KotlinLogging.logger {}

/**
 * An adapter to communicate with the Image microservice.
 */
@Component
open class ImageClientImpl : ImageClient {

    /**
     * Makes a simple HTTP Get request to the Image microservice.
     *
     * @return The path to the image
     */
    override fun getImagePath(): String? {
        val httpClient = HttpClient.newHttpClient()
        val httpGet = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("http://localhost:50005/image-path"))
            .build()

        return try {
            logger.info { "Sending request to fetch image path" }
            val httpResponse = httpClient.send(httpGet, BodyHandlers.ofString())
            logResponse(httpResponse)
            httpResponse.body()
        } catch (ioe: IOException) {
            logger.error(ioe) { "Failure occurred while getting image path" }
            null
        } catch (ie: InterruptedException) {
            logger.error(ie) { "Failure occurred while getting image path" }
            Thread.currentThread().interrupt()
            null
        }
    }

    private fun logResponse(httpResponse: HttpResponse<String>) {
        if (isSuccessResponse(httpResponse.statusCode())) {
            logger.info { "Image path received successfully" }
        } else {
            logger.warn { "Image path request failed" }
        }
    }

    private fun isSuccessResponse(responseCode: Int): Boolean {
        return responseCode in 200..299
    }
}
