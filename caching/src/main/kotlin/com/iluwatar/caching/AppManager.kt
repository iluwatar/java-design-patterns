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

// ABOUTME: Application manager coordinating cache and database operations.
// ABOUTME: Routes operations to appropriate cache strategy based on configured policy.
package com.iluwatar.caching

import com.iluwatar.caching.database.DbManager
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * AppManager helps to bridge the gap in communication between the main class and the application's
 * back-end. DB connection is initialized through this class. The chosen caching strategy/policy is
 * also initialized here. Before the cache can be used, the size of the cache has to be set.
 * Depending on the chosen caching policy, AppManager will call the appropriate function in the
 * CacheStore class.
 */
class AppManager(
    private val dbManager: DbManager,
) {
    /** Caching Policy. */
    private var cachingPolicy: CachingPolicy? = null

    /** Cache Store. */
    private val cacheStore: CacheStore = CacheStore(dbManager)

    /**
     * Developer/Tester is able to choose whether the application should use MongoDB as its underlying
     * data storage or a simple Java data structure to (temporarily) store the data/objects during
     * runtime.
     */
    fun initDb() {
        dbManager.connect()
    }

    /**
     * Initialize caching policy.
     *
     * @param policy caching policy to use
     */
    fun initCachingPolicy(policy: CachingPolicy) {
        cachingPolicy = policy
        if (cachingPolicy == CachingPolicy.BEHIND) {
            Runtime.getRuntime().addShutdownHook(Thread { cacheStore.flushCache() })
        }
        cacheStore.clearCache()
    }

    /**
     * Find user account.
     *
     * @param userId user ID
     * @return UserAccount or null
     */
    fun find(userId: String): UserAccount? {
        logger.info { "Trying to find $userId in cache" }
        return when (cachingPolicy) {
            CachingPolicy.THROUGH, CachingPolicy.AROUND -> cacheStore.readThrough(userId)
            CachingPolicy.BEHIND -> cacheStore.readThroughWithWriteBackPolicy(userId)
            CachingPolicy.ASIDE -> findAside(userId)
            null -> null
        }
    }

    /**
     * Save user account.
     *
     * @param userAccount user account to save
     */
    fun save(userAccount: UserAccount) {
        logger.info { "Save record!" }
        when (cachingPolicy) {
            CachingPolicy.THROUGH -> cacheStore.writeThrough(userAccount)
            CachingPolicy.AROUND -> cacheStore.writeAround(userAccount)
            CachingPolicy.BEHIND -> cacheStore.writeBehind(userAccount)
            CachingPolicy.ASIDE -> saveAside(userAccount)
            null -> {}
        }
    }

    /**
     * Returns String.
     *
     * @return String representation of cache content
     */
    fun printCacheContent(): String = cacheStore.print()

    /**
     * Cache-Aside save user account helper.
     *
     * @param userAccount user account to save
     */
    private fun saveAside(userAccount: UserAccount) {
        dbManager.updateDb(userAccount)
        cacheStore.invalidate(userAccount.userId)
    }

    /**
     * Cache-Aside find user account helper.
     *
     * @param userId user ID
     * @return UserAccount or null
     */
    private fun findAside(userId: String): UserAccount? =
        cacheStore.get(userId) ?: run {
            val userAccount = dbManager.readFromDb(userId)
            userAccount?.let { cacheStore.set(userId, it) }
            userAccount
        }
}