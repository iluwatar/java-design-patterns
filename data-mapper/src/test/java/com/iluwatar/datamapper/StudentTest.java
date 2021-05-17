/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.datamapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Student}.
 */
final class StudentTest {

  /**
   * This API tests the equality behaviour of Student object Object Equality should work as per
   * logic defined in equals method
   *
   * @throws Exception if any execution error during test
   */
  @Test
  void testEquality() throws Exception {

    /* Create some students */
    final var firstStudent = new Student(1, "Adam", 'A');
    final var secondStudent = new Student(2, "Donald", 'B');
    final var secondSameStudent = new Student(2, "Donald", 'B');

    /* Check equals functionality: should return 'true' */
    assertEquals(firstStudent, firstStudent);

    /* Check equals functionality: should return 'false' */
    assertNotEquals(firstStudent, secondStudent);

    /* Check equals functionality: should return 'true' */
    assertEquals(secondStudent, secondSameStudent);
  }
}
