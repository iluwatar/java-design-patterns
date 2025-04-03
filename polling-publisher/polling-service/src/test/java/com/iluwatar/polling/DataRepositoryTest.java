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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class DataRepositoryTest {

  private DataRepository repository;

  @BeforeEach
  void setUp() {
    repository = new DataRepository(); // Initialize before each test
  }

  @Test
  void testSaveAndFindById() {
    repository.save(1, "Test Data");

    String result = repository.findById(1);

    assertEquals("Test Data", result, "The retrieved data should match the stored value.");
  }

  @Test
  void testFindById_NotFound() {
    String result = repository.findById(99);

    assertEquals("Data not found", result, "Should return 'Data not found' for missing entries.");
  }

  @Test
  void testDelete() {
    repository.save(2, "To be deleted");
    repository.delete(2);

    String result = repository.findById(2);

    assertEquals("Data not found", result, "Deleted data should not be retrievable.");
  }

  @Test
  void testFindAll() {
    repository.save(1, "First");
    repository.save(2, "Second");

    Map<Integer, String> allData = repository.findAll();

    assertEquals(2, allData.size(), "The repository should contain two items.");
    assertTrue(allData.containsKey(1) && allData.containsKey(2), "Both keys should exist.");
  }
}
