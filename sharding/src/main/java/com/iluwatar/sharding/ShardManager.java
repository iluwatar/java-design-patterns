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

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class for ShardManager.
 */
@Slf4j
public abstract class ShardManager {

  protected Map<Integer, Shard> shardMap;

  public ShardManager() {
    shardMap = new HashMap<>();
  }

  /**
   * Add a provided shard instance to shardMap.
   *
   * @param shard new shard instance.
   * @return {@code true} if succeed to add the new instance.
   *         {@code false} if the shardId is already existed.
   */
  public boolean addNewShard(final Shard shard) {
    var shardId = shard.getId();
    if (!shardMap.containsKey(shardId)) {
      shardMap.put(shardId, shard);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Remove a shard instance by provided Id.
   *
   * @param shardId Id of shard instance to remove.
   * @return {@code true} if removed. {@code false} if the shardId is not existed.
   */
  public boolean removeShardById(final int shardId) {
    if (shardMap.containsKey(shardId)) {
      shardMap.remove(shardId);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Get shard instance by provided shardId.
   *
   * @param shardId id of shard instance to get
   * @return required shard instance
   */
  public Shard getShardById(final int shardId) {
    return shardMap.get(shardId);
  }

  /**
   * Store data in proper shard instance.
   *
   * @param data new data
   * @return id of shard that the data is stored in
   */
  public abstract int storeData(final Data data);

  /**
   * Allocate proper shard to provided data.
   *
   * @param data new data
   * @return id of shard that the data should be stored
   */
  protected abstract int allocateShard(final Data data);

}
