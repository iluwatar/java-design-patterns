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
package com.iluwatar.sharding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for ShardManager class. */
class ShardManagerTest {

  private ShardManager shardManager;

  /** Initialize shardManager instance. */
  @BeforeEach
  void setup() {
    shardManager = new TestShardManager();
  }

  @Test
  void testAddNewShard() {
    try {
      var shard = new Shard(1);
      shardManager.addNewShard(shard);
      var field = ShardManager.class.getDeclaredField("shardMap");
      field.setAccessible(true);
      var map = (Map<Integer, Shard>) field.get(shardManager);
      assertEquals(1, map.size());
      assertEquals(shard, map.get(1));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to modify field access.");
    }
  }

  @Test
  void testRemoveShardById() {
    try {
      var shard = new Shard(1);
      shardManager.addNewShard(shard);
      boolean flag = shardManager.removeShardById(1);
      var field = ShardManager.class.getDeclaredField("shardMap");
      field.setAccessible(true);
      var map = (Map<Integer, Shard>) field.get(shardManager);
      assertTrue(flag);
      assertEquals(0, map.size());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("Fail to modify field access.");
    }
  }

  @Test
  void testGetShardById() {
    var shard = new Shard(1);
    shardManager.addNewShard(shard);
    var tmpShard = shardManager.getShardById(1);
    assertEquals(shard, tmpShard);
  }

  static class TestShardManager extends ShardManager {

    @Override
    public int storeData(Data data) {
      return 0;
    }

    @Override
    protected int allocateShard(Data data) {
      return 0;
    }
  }
}
