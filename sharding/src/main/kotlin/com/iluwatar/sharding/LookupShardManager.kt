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

// ABOUTME: ShardManager implementation using lookup-based sharding strategy.
// ABOUTME: Maintains a lookup map to route requests to the shard containing the data.
package com.iluwatar.sharding

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

/**
 * ShardManager with lookup strategy. In this strategy the sharding logic implements a map that
 * routes a request for data to the shard that contains that data by using the shard key.
 */
class LookupShardManager : ShardManager() {

    internal val lookupMap: MutableMap<Int, Int> = HashMap()

    override fun storeData(data: Data): Int {
        val shardId = allocateShard(data)
        lookupMap[data.key] = shardId
        val shard = shardMap[shardId]
        shard?.storeData(data)
        logger.info { "$data is stored in Shard $shardId" }
        return shardId
    }

    override fun allocateShard(data: Data): Int {
        val key = data.key
        return if (lookupMap.containsKey(key)) {
            lookupMap[key]!!
        } else {
            val shardCount = shardMap.size
            SecureRandom().nextInt(shardCount - 1) + 1
        }
    }
}
