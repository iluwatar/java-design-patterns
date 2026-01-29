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

package com.iluwatar.partialresponse

// ABOUTME: Entry point demonstrating the Partial Response design pattern.
// ABOUTME: Client requests specific fields from a VideoResource server to get full or partial responses.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Partial Response pattern is a design pattern in which client specifies fields to fetch to
 * serve. Here the main function is playing as client for [VideoResource] server. Client asks for
 * specific fields information in video to server.
 *
 * [VideoResource] acts as server to serve video information.
 */
fun main() {
    val videos = mapOf(
        1 to Video(1, "Avatar", 178, "epic science fiction film", "James Cameron", "English"),
        2 to Video(2, "Godzilla Resurgence", 120, "Action & drama movie|", "Hideaki Anno", "Japanese"),
        3 to Video(3, "Interstellar", 169, "Adventure & Sci-Fi", "Christopher Nolan", "English")
    )
    val videoResource = VideoResource(FieldJsonMapper(), videos)

    logger.info { "Retrieving full response from server:-" }
    logger.info { "Get all video information:" }
    val videoDetails = videoResource.getDetails(1)
    logger.info { videoDetails }

    logger.info { "----------------------------------------------------------" }

    logger.info { "Retrieving partial response from server:-" }
    logger.info { "Get video @id, @title, @director:" }
    val specificFieldsDetails = videoResource.getDetails(3, "id", "title", "director")
    logger.info { specificFieldsDetails }

    logger.info { "Get video @id, @length:" }
    val videoLength = videoResource.getDetails(3, "id", "length")
    logger.info { videoLength }
}
