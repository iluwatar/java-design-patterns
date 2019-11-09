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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for RangeShardManager class.
 */
public class RangeShardManagerTest {

  private RangeShardManager rangeShardManager;

  /**
   * Initialize rangeShardManager instance.
   */
  @Before
  public void setup() {
    rangeShardManager = new RangeShardManager();
    var shard1 = new Shard(1);
    var shard2 = new Shard(2);
    var shard3 = new Shard(3);
    rangeShardManager.addNewShard(shard1);
    rangeShardManager.addNewShard(shard2);
    rangeShardManager.addNewShard(shard3);
  }

  @Test
  public void testStoreData() {
    var data = new Data(1, "test", Data.DataType.type1);
    rangeShardManager.storeData(data);
    Assert.assertEquals(data, rangeShardManager.getShardById(1).getDataById(1));
  }

}
