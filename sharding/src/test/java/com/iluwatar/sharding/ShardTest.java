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

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit tests for Shard class.
 */
public class ShardTest {

  private Data data;

  private Shard shard;

  @Before
  public void setup() {
    data = new Data(1, "test", Data.DataType.type1);
    shard = new Shard(1);
  }

  @After
  public void tearDown() {}

  @Test
  public void testStoreData() {
    try {
      shard.storeData(data);
      var field = Shard.class.getDeclaredField("dataStore");
      field.setAccessible(true);
      Map<Integer, Data> dataMap = (Map<Integer, Data>) field.get(shard);
      Assert.assertEquals(1, dataMap.size());
      Assert.assertEquals(data, dataMap.get(1));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      Assert.fail("Fail to modify field access.");
    }

  }

  @Test
  public void testClearData() {
    try {
      Map<Integer, Data> dataMap = new HashMap<>();
      dataMap.put(1, data);
      var field = Shard.class.getDeclaredField("dataStore");
      field.setAccessible(true);
      field.set(shard, dataMap);
      shard.clearData();
      dataMap = (Map<Integer, Data>) field.get(shard);
      Assert.assertEquals(0, dataMap.size());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      Assert.fail("Fail to modify field access.");
    }
  }
}
