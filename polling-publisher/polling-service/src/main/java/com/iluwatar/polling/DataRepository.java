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

package com.iluwatar.polling;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

/** Data repository to keep or store data. */
@Repository
public class DataRepository {

  private final Map<Integer, String> dataStorage = new HashMap<>();

  /** init after map creation ... to put dummy data. */
  @PostConstruct
  public void init() {
    // Injecting dummy data at startup
    dataStorage.put(2, "Initial Dummy Data - two - 2");
    dataStorage.put(3, "Initial Dummy Data - three - 3");
    dataStorage.put(4, "Initial Dummy Data - four - 4");
  }

  /** Save data to the repository. */
  public void save(int id, String value) {
    dataStorage.put(id, value);
  }

  /** Retrieve data by ID. */
  public String findById(int id) {
    return dataStorage.getOrDefault(id, "Data not found");
  }

  /** Delete data by ID. */
  public void delete(int id) {
    dataStorage.remove(id);
  }

  /** Get all data. */
  public Map<Integer, String> findAll() {
    return dataStorage;
  }
}
