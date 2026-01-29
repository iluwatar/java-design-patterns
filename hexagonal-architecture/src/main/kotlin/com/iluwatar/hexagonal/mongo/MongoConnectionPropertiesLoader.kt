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

// ABOUTME: Utility for loading MongoDB connection properties from a file.
// ABOUTME: Falls back to default localhost settings if properties file is unavailable.
package com.iluwatar.hexagonal.mongo

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.FileInputStream
import java.util.Properties

private val logger = KotlinLogging.logger {}

/**
 * Mongo connection properties loader.
 */
object MongoConnectionPropertiesLoader {

    private const val DEFAULT_HOST = "localhost"
    private const val DEFAULT_PORT = 27017

    /**
     * Try to load connection properties from file. Fall back to default connection properties.
     */
    fun load() {
        var host = DEFAULT_HOST
        var port = DEFAULT_PORT
        val path = System.getProperty("hexagonal.properties.path")
        val properties = Properties()
        if (path != null) {
            try {
                FileInputStream(path).use { fin ->
                    properties.load(fin)
                    host = properties.getProperty("mongo-host")
                    port = properties.getProperty("mongo-port").toInt()
                }
            } catch (e: Exception) {
                // error occurred, use default properties
                logger.error(e) { "Error occurred: " }
            }
        }
        System.setProperty("mongo-host", host)
        System.setProperty("mongo-port", "$port")
    }
}
