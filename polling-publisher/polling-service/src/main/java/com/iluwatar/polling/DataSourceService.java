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

import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for keep the events.
 */
@Service
public class DataSourceService {

  private final DataRepository repository;

  /**
   * Constructor & Scheduler to push random data.
   */
  public DataSourceService(DataRepository repository) {
    this.repository = repository;

    // Start a separate thread to add data every 3 seconds
    new Thread(() -> {
      Random random = new Random();
      while (true) {
        try {
          Thread.sleep(3000); // Add data every 3 seconds
          int id = random.nextInt(100); // Random ID
          String value = "Auto-Data-" + id;
          this.addData(id, value);
          System.out.println("🔵 Data Added: " + id + " -> " + value);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }).start();

  }

  public void addData(int id, String value) {
    repository.save(id, value);
  }

  public String getData(int id) {
    return repository.findById(id);
  }

  public void removeData(int id) {
    repository.delete(id);
  }

  public Map<Integer, String> getAllData() {
    return repository.findAll();
  }
}
