/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for ShardManager class.
 */
public class ShardManagerTest {

  private ShardManager shardManager;

  /**
   * Initialize shardManager instance.
   */
  @Before
  public void setup() {
    shardManager = new TestShardManager();
  }

  @After
  public void tearDown() {

  }

  @Test
  public void testAddNewShard() {
    try {
      var shard = new Shard(1);
      shardManager.addNewShard(shard);
      var field = ShardManager.class.getDeclaredField("shardMap");
      field.setAccessible(true);
      Map<Integer, Shard> map = (Map<Integer, Shard>) field.get(shardManager);
      Assert.assertEquals(1, map.size());
      Assert.assertEquals(shard, map.get(1));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      Assert.fail("Fail to modify field access.");
    }
  }

  @Test
  public void testRemoveShardById() {
    try {
      var shard = new Shard(1);
      shardManager.addNewShard(shard);
      boolean flag = shardManager.removeShardById(1);
      var field = ShardManager.class.getDeclaredField("shardMap");
      field.setAccessible(true);
      Map<Integer, Shard> map = (Map<Integer, Shard>) field.get(shardManager);
      Assert.assertEquals(true, flag);
      Assert.assertEquals(0, map.size());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      Assert.fail("Fail to modify field access.");
    }
  }

  @Test
  public void testGetShardById() {
    Shard shard = new Shard(1);
    shardManager.addNewShard(shard);
    Shard tmpShard = shardManager.getShardById(1);
    Assert.assertEquals(shard, tmpShard);
  }

  class TestShardManager extends ShardManager {

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
