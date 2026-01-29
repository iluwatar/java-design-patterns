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

// ABOUTME: Implements caching strategies (read-through, write-through, write-around, write-behind).
// ABOUTME: Acts as an intermediary between the application and the database with cache layer.
package com.iluwatar.caching

import com.iluwatar.caching.database.DbManager
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The caching strategies are implemented in this class.
 */
class CacheStore(
    private val dbManager: DbManager,
) {
    /** Lru cache see [LruCache]. */
    private var cache: LruCache? = null

    init {
        initCapacity(CAPACITY)
    }

    /**
     * Init cache capacity.
     *
     * @param capacity cache capacity
     */
    fun initCapacity(capacity: Int) {
        val currentCache = cache
        if (currentCache == null) {
            cache = LruCache(capacity)
        } else {
            currentCache.setCapacity(capacity)
        }
    }

    /**
     * Get user account using read-through cache.
     *
     * @param userId user ID
     * @return UserAccount
     */
    fun readThrough(userId: String): UserAccount? {
        cache?.let { c ->
            if (c.contains(userId)) {
                logger.info { "# Found in Cache!" }
                return c.get(userId)
            }
        }
        logger.info { "# Not found in cache! Go to DB!!" }
        val userAccount = dbManager.readFromDb(userId)
        userAccount?.let { cache?.set(userId, it) }
        return userAccount
    }

    /**
     * Get user account using write-through cache.
     *
     * @param userAccount user account to write
     */
    fun writeThrough(userAccount: UserAccount) {
        cache?.let { c ->
            if (c.contains(userAccount.userId)) {
                dbManager.updateDb(userAccount)
            } else {
                dbManager.writeToDb(userAccount)
            }
            c.set(userAccount.userId, userAccount)
        }
    }

    /**
     * Get user account using write-around cache.
     *
     * @param userAccount user account to write
     */
    fun writeAround(userAccount: UserAccount) {
        cache?.let { c ->
            if (c.contains(userAccount.userId)) {
                dbManager.updateDb(userAccount)
                // Cache data has been updated -- remove older
                c.invalidate(userAccount.userId)
                // version from cache.
            } else {
                dbManager.writeToDb(userAccount)
            }
        }
    }

    /**
     * Get user account using read-through cache with write-back policy.
     *
     * @param userId user ID
     * @return UserAccount
     */
    fun readThroughWithWriteBackPolicy(userId: String): UserAccount? {
        cache?.let { c ->
            if (c.contains(userId)) {
                logger.info { "# Found in cache!" }
                return c.get(userId)
            }
        }
        logger.info { "# Not found in Cache!" }
        val userAccount = dbManager.readFromDb(userId)
        cache?.let { c ->
            if (c.isFull()) {
                logger.info { "# Cache is FULL! Writing LRU data to DB..." }
                c.getLruData()?.let { dbManager.upsertDb(it) }
            }
            userAccount?.let { c.set(userId, it) }
        }
        return userAccount
    }

    /**
     * Set user account.
     *
     * @param userAccount user account to write behind
     */
    fun writeBehind(userAccount: UserAccount) {
        cache?.let { c ->
            if (c.isFull() && !c.contains(userAccount.userId)) {
                logger.info { "# Cache is FULL! Writing LRU data to DB..." }
                c.getLruData()?.let { dbManager.upsertDb(it) }
            }
            c.set(userAccount.userId, userAccount)
        }
    }

    /** Clears cache. */
    fun clearCache() {
        cache?.clear()
    }

    /** Writes remaining content in the cache into the DB. */
    fun flushCache() {
        logger.info { "# flushCache..." }
        cache?.getCacheDataInListForm()?.forEach { dbManager.updateDb(it) }
            ?: emptyList<UserAccount>()
        dbManager.disconnect()
    }

    /**
     * Print user accounts.
     *
     * @return String representation of cache content
     */
    fun print(): String {
        val cacheData = cache?.getCacheDataInListForm() ?: emptyList()
        return cacheData.joinToString(
            separator = "\n",
            prefix = "\n--CACHE CONTENT--\n",
            postfix = "\n----",
        ) { it.toString() }
    }

    /**
     * Delegate to backing cache store.
     *
     * @param userId user ID
     * @return UserAccount or null
     */
    fun get(userId: String): UserAccount? = cache?.get(userId)

    /**
     * Delegate to backing cache store.
     *
     * @param userId user ID
     * @param userAccount user account to set
     */
    fun set(
        userId: String,
        userAccount: UserAccount,
    ) {
        cache?.set(userId, userAccount)
    }

    /**
     * Delegate to backing cache store.
     *
     * @param userId user ID to invalidate
     */
    fun invalidate(userId: String) {
        cache?.invalidate(userId)
    }

    companion object {
        /** Cache capacity. */
        private const val CAPACITY = 3
    }
}