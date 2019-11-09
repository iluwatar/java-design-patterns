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

/**
 * Sharding pattern means dividing a data store into a set of horizontal partitions
 * or shards. This pattern can improve scalability when storing and accessing large
 * volumes of data.
 */
public class App {

  /**
   * Program main entry point.
   * @param args program runtime arguments
   */
  public static void main(String[] args) {

    var data1 = new Data(1, "data1", Data.DataType.type1);
    var data2 = new Data(2, "data2", Data.DataType.type2);
    var data3 = new Data(3, "data3", Data.DataType.type3);
    var data4 = new Data(4, "data4", Data.DataType.type1);

    var shard1 = new Shard(1);
    var shard2 = new Shard(2);
    var shard3 = new Shard(3);

    ShardManager manager = new LookupShardManager();
    manager.addNewShard(shard1);
    manager.addNewShard(shard2);
    manager.addNewShard(shard3);
    manager.storeData(data1);
    manager.storeData(data2);
    manager.storeData(data3);
    manager.storeData(data4);

    shard1.clearData();
    shard2.clearData();
    shard3.clearData();

    manager = new RangeShardManager();
    manager.addNewShard(shard1);
    manager.addNewShard(shard2);
    manager.addNewShard(shard3);
    manager.storeData(data1);
    manager.storeData(data2);
    manager.storeData(data3);
    manager.storeData(data4);

    shard1.clearData();
    shard2.clearData();
    shard3.clearData();

    manager = new HashShardManager();
    manager.addNewShard(shard1);
    manager.addNewShard(shard2);
    manager.addNewShard(shard3);
    manager.storeData(data1);
    manager.storeData(data2);
    manager.storeData(data3);
    manager.storeData(data4);

    shard1.clearData();
    shard2.clearData();
    shard3.clearData();
  }

}
