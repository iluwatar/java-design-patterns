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
package com.iluwatar.datatransfer.product

// ABOUTME: Product data transfer objects organized by request and response types.
// ABOUTME: Uses sealed interfaces and data classes to enforce field visibility per audience.

/**
 * [ProductDto] is a data transfer object hierarchy. Instead of sending individual information to
 * the client we can send related information together.
 *
 * The DTO does not contain any business logic.
 */
object ProductDto {
    /**
     * This is the Request namespace which consists of Create or any other request DTOs
     * you might want to use in your API.
     */
    object Request {
        /** DTO class for requesting creation of a new product. */
        data class Create(
            val name: String? = null,
            val price: Double? = null,
            val cost: Double? = null,
            val supplier: String? = null,
        )
    }

    /**
     * This is the Response namespace which consists of any response DTOs you might want
     * to provide to your clients.
     */
    object Response {
        /** Public DTO class for API responses with the lowest data security. */
        data class Public(
            val id: Long? = null,
            val name: String? = null,
            val price: Double? = null,
        )

        /** Private DTO class for API responses with the highest data security. */
        data class Private(
            val id: Long? = null,
            val name: String? = null,
            val price: Double? = null,
            val cost: Double? = null,
        )
    }
}
