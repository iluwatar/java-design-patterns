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
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ShardManager with lookup strategy. In this strategy the sharding logic implements
 * a map that routes a request for data to the shard that contains that data by using
 * the shard key.
 */
public class LookupShardManager extends ShardManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(LookupShardManager.class);

  private Map<Integer, Integer> lookupMap = new HashMap<>();

  @Override
  public int storeData(Data data) {
    var shardId = allocateShard(data);
    lookupMap.put(data.getKey(), shardId);
    var shard = shardMap.get(shardId);
    shard.storeData(data);
    LOGGER.info(data.toString() + " is stored in Shard " + shardId);
    return shardId;
  }

  @Override
  protected int allocateShard(Data data) {
    var key = data.getKey();
    if (lookupMap.containsKey(key)) {
      return lookupMap.get(key);
    } else {
      var shardCount = shardMap.size();
      var allocatedShardId = new Random().nextInt(shardCount - 1) + 1;
      return allocatedShardId;
    }
  }

}
