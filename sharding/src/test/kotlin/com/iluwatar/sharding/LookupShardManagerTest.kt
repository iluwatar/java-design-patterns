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

// ABOUTME: Unit tests for LookupShardManager class.
// ABOUTME: Tests lookup-based sharding strategy.
package com.iluwatar.sharding

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Unit tests for LookupShardManager class.
 */
class LookupShardManagerTest {

    private lateinit var lookupShardManager: LookupShardManager

    /**
     * Initialize lookupShardManager instance.
     */
    @BeforeEach
    fun setup() {
        lookupShardManager = LookupShardManager()
        val shard1 = Shard(1)
        val shard2 = Shard(2)
        val shard3 = Shard(3)
        lookupShardManager.addNewShard(shard1)
        lookupShardManager.addNewShard(shard2)
        lookupShardManager.addNewShard(shard3)
    }

    @Test
    fun testStoreData() {
        val data = Data(1, "test", Data.DataType.TYPE_1)
        lookupShardManager.storeData(data)
        val lookupMap = lookupShardManager.lookupMap
        val shardId = lookupMap[1]!!
        val shard = lookupShardManager.getShardById(shardId)
        assertEquals(data, shard?.getDataById(1))
    }
}
