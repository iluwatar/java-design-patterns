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

// ABOUTME: Central registry that fetches services from cache or creates new ones via InitContext.
// ABOUTME: Implements the Service Locator pattern to decouple service consumers from service lookup.

/**
 * The service locator module. Will fetch service from cache, otherwise creates a fresh service and
 * update cache
 */
object ServiceLocator {

    private val serviceCache = ServiceCache()

    /**
     * Fetch the service with the name param from the cache first, if no service is found, lookup the
     * service from the [InitContext] and then add the newly created service into the cache map
     * for future requests.
     *
     * @param serviceJndiName a string
     * @return [Service] or null
     */
    fun getService(serviceJndiName: String): Service? {
        var serviceObj = serviceCache.getService(serviceJndiName)
        if (serviceObj == null) {
            /*
             * If we are unable to retrieve anything from cache, then lookup the service and add it in the
             * cache map
             */
            val ctx = InitContext()
            serviceObj = ctx.lookup(serviceJndiName) as? Service
            if (serviceObj != null) { // Only cache a service if it actually exists
                serviceCache.addService(serviceObj)
            }
        }
        return serviceObj
    }
}
