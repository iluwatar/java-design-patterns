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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.*;

public class DataSourceServiceTest {

  private DataRepository repository;
  private DataSourceService service;

  @BeforeEach
  void setUp() {
    repository = new DataRepository();
    service = new DataSourceService(repository);
  }

  @Test
  void testAddData() {
    service.addData(1, "Test Data");

    assertEquals("Test Data", repository.findById(1));
  }

  @Test
  void testGetData() {
    repository.save(1, "Test Data");

    String result = service.getData(1);

    assertEquals("Test Data", result, "The retrieved data should match.");
  }

  @Test
  void testRemoveData() {
    repository.save(2, "Some Data");

    service.removeData(2);

    assertEquals(
        "Data not found", repository.findById(2), "Deleted data should not be retrievable.");
  }

  @Test
  void testGetAllData() {
    Map<Integer, String> result = service.getAllData();

    int size = result.size();
    repository.save(1, "First");
    repository.save(2, "Second");

    Map<Integer, String> result1 = service.getAllData();

    assertEquals(size + 2, result.size(), "Should return all stored data.");
    assertEquals("First", result.get(1), "Value for key 1 should be 'First'.");
    assertEquals("Second", result.get(2), "Value for key 2 should be 'Second'.");
  }
}
