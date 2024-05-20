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

import lombok.Getter;
import lombok.Setter;

/**
 * Basic data structure for each tuple stored in data shards.
 */
@Getter
@Setter
public class Data {

  private int key;

  private String value;

  private DataType type;

  /**
   * Constructor of Data class.
   * @param key data key
   * @param value data vlue
   * @param type data type
   */
  public Data(final int key, final String value, final DataType type) {
    this.key = key;
    this.value = value;
    this.type = type;
  }

  enum DataType {
    TYPE_1, TYPE_2, TYPE_3
  }

  @Override
  public String toString() {
    return "Data {" + "key="
        + key + ", value='" + value
        + '\'' + ", type=" + type + '}';
  }
}

