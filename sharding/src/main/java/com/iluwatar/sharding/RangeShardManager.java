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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ShardManager with range strategy. This strategy groups related items together in the same shard,
 * and orders them by shard key.
 */
public class RangeShardManager extends ShardManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(RangeShardManager.class);

  @Override
  public int storeData(Data data) {
    var shardId = allocateShard(data);
    var shard = shardMap.get(shardId);
    shard.storeData(data);
    LOGGER.info(data.toString() + " is stored in Shard " + shardId);
    return shardId;
  }

  @Override
  protected int allocateShard(Data data) {
    var type = data.getType();
    switch (type) {
      case type1:
        return 1;
      case type2:
        return 2;
      case type3:
        return 3;
      default:
        return -1;
    }
  }

}
