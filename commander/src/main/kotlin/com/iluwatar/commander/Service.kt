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

// ABOUTME: Service class is an abstract class extended by all services in this example.
// ABOUTME: Provides common functionality for receiving requests, updating database, and generating IDs.
package com.iluwatar.commander

import com.iluwatar.commander.exceptions.DatabaseUnavailableException
import java.security.SecureRandom
import java.util.Hashtable

abstract class Service(
    internal val database: Database<*>?,
    vararg exc: Exception,
) {
    var exceptionsList: MutableList<Exception> = exc.toMutableList()

    @Throws(DatabaseUnavailableException::class)
    abstract fun receiveRequest(vararg parameters: Any?): String?

    @Throws(DatabaseUnavailableException::class)
    internal abstract fun updateDb(vararg parameters: Any?): String?

    internal fun generateId(): String {
        val random = StringBuilder()
        while (random.length < 12) {
            val index = (RANDOM.nextFloat() * ALL_CHARS.length).toInt()
            random.append(ALL_CHARS[index])
        }
        var id = random.toString()
        if (USED_IDS[id] != null) {
            while (USED_IDS[id] == true) {
                id = generateId()
            }
        }
        return id
    }

    companion object {
        private val RANDOM = SecureRandom()
        private const val ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        private val USED_IDS = Hashtable<String, Boolean>()
    }
}