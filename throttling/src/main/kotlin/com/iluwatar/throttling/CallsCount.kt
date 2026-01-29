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
package com.iluwatar.throttling

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

// ABOUTME: Thread-safe counter for tracking API calls per tenant.
// ABOUTME: Supports adding tenants, incrementing counts, and resetting all counters.

private val logger = KotlinLogging.logger {}

/**
 * A class to keep track of the counter of different Tenants.
 */
class CallsCount {
    private val tenantCallsCount = ConcurrentHashMap<String, AtomicLong>()

    /**
     * Add a new tenant to the map.
     *
     * @param tenantName name of the tenant.
     */
    fun addTenant(tenantName: String) {
        tenantCallsCount.putIfAbsent(tenantName, AtomicLong(0))
    }

    /**
     * Increment the count of the specified tenant.
     *
     * @param tenantName name of the tenant.
     */
    fun incrementCount(tenantName: String) {
        tenantCallsCount[tenantName]?.incrementAndGet()
    }

    /**
     * Get count of tenant based on tenant name.
     *
     * @param tenantName name of the tenant.
     * @return the count of the tenant.
     */
    fun getCount(tenantName: String): Long {
        return tenantCallsCount[tenantName]?.get() ?: 0
    }

    /**
     * Resets the count of all the tenants in the map.
     */
    fun reset() {
        tenantCallsCount.replaceAll { _, _ -> AtomicLong(0) }
        logger.info { "reset counters" }
    }
}
