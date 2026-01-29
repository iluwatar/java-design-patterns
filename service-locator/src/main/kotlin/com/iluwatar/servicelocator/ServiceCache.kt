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
package com.iluwatar.servicelocator

import io.github.oshai.kotlinlogging.KotlinLogging

// ABOUTME: Cache implementation for storing and retrieving services by name.
// ABOUTME: Returns cached services on subsequent requests to avoid repeated lookups.

private val logger = KotlinLogging.logger {}

/**
 * The service cache implementation which will cache services that are being created. On first hit,
 * the cache will be empty and thus any service that is being requested, will be created fresh and
 * then placed into the cache map. On next hit, if same service name will be requested, it will be
 * returned from the cache
 */
class ServiceCache {

    private val serviceCache: MutableMap<String, Service> = HashMap()

    /**
     * Get the service from the cache. null if no service is found matching the name
     *
     * @param serviceName a string
     * @return [Service] or null
     */
    fun getService(serviceName: String): Service? {
        return serviceCache[serviceName]?.also { cachedService ->
            logger.info { "(cache call) Fetched service ${cachedService.name}(${cachedService.id}) from cache... !" }
        }
    }

    /**
     * Adds the service into the cache map.
     *
     * @param newService a [Service]
     */
    fun addService(newService: Service) {
        serviceCache[newService.name] = newService
    }
}
