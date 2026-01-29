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

// ABOUTME: LRU (Least Recently Used) cache implementation using a doubly-linked list and hash map.
// ABOUTME: Provides O(1) access, insertion, and eviction of least recently used entries.
package com.iluwatar.caching

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Data structure/implementation of the application's cache. The data structure consists of a hash
 * table attached with a doubly linked-list. The linked-list helps in capturing and maintaining the
 * LRU data in the cache. When a data is queried (from the cache), added (to the cache), or updated,
 * the data is moved to the front of the list to depict itself as the most-recently-used data. The
 * LRU data is always at the end of the list.
 */
class LruCache(
    private var capacity: Int,
) {
    /** Static class Node. */
    internal class Node(
        val userId: String,
        var userAccount: UserAccount,
    ) {
        var previous: Node? = null
        var next: Node? = null
    }

    /** Cache HashMap. */
    private val cache: MutableMap<String, Node> = HashMap()

    /** Head. */
    private var head: Node? = null

    /** End. */
    private var end: Node? = null

    /**
     * Get user account.
     *
     * @param userId user ID
     * @return UserAccount or null if not found
     */
    fun get(userId: String): UserAccount? {
        val node = cache[userId] ?: return null
        remove(node)
        setHead(node)
        return node.userAccount
    }

    /**
     * Remove node from linked list.
     *
     * @param node node to remove
     */
    private fun remove(node: Node) {
        if (node.previous != null) {
            node.previous?.next = node.next
        } else {
            head = node.next
        }
        if (node.next != null) {
            node.next?.previous = node.previous
        } else {
            end = node.previous
        }
    }

    /**
     * Move node to the front of the list.
     *
     * @param node node to set as head
     */
    private fun setHead(node: Node) {
        node.next = head
        node.previous = null
        head?.previous = node
        head = node
        if (end == null) {
            end = head
        }
    }

    /**
     * Set user account.
     *
     * @param userId user ID
     * @param userAccount user account to set
     */
    fun set(
        userId: String,
        userAccount: UserAccount,
    ) {
        val existingNode = cache[userId]
        if (existingNode != null) {
            existingNode.userAccount = userAccount
            remove(existingNode)
            setHead(existingNode)
        } else {
            val newNode = Node(userId, userAccount)
            if (cache.size >= capacity) {
                logger.info { "# Cache is FULL! Removing ${end?.userId} from cache..." }
                end?.userId?.let { cache.remove(it) } // remove LRU data from cache.
                end?.let { remove(it) }
                setHead(newNode)
            } else {
                setHead(newNode)
            }
            cache[userId] = newNode
        }
    }

    /**
     * Check if Cache contains the userId.
     *
     * @param userId user ID
     * @return true if cache contains the user
     */
    fun contains(userId: String): Boolean = cache.containsKey(userId)

    /**
     * Invalidate cache for user.
     *
     * @param userId user ID to invalidate
     */
    fun invalidate(userId: String) {
        val toBeRemoved = cache.remove(userId)
        if (toBeRemoved != null) {
            logger.info { "# $userId has been updated! Removing older version from cache..." }
            remove(toBeRemoved)
        }
    }

    /**
     * Check if the cache is full.
     *
     * @return true if cache is full
     */
    fun isFull(): Boolean = cache.size >= capacity

    /**
     * Get LRU data.
     *
     * @return least recently used UserAccount
     */
    fun getLruData(): UserAccount? = end?.userAccount

    /** Clear cache. */
    fun clear() {
        head = null
        end = null
        cache.clear()
    }

    /**
     * Returns cache data in list form.
     *
     * @return list of UserAccount in cache
     */
    fun getCacheDataInListForm(): List<UserAccount> {
        val listOfCacheData = mutableListOf<UserAccount>()
        var temp = head
        while (temp != null) {
            listOfCacheData.add(temp.userAccount)
            temp = temp.next
        }
        return listOfCacheData
    }

    /**
     * Set cache capacity.
     *
     * @param newCapacity new capacity
     */
    fun setCapacity(newCapacity: Int) {
        if (capacity > newCapacity) {
            // Behavior can be modified to accommodate
            // for decrease in cache size. For now, we'll
            clear()
            // just clear the cache.
        } else {
            this.capacity = newCapacity
        }
    }
}