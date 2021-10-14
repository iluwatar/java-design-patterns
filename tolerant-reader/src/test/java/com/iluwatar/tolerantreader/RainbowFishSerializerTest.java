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

package com.iluwatar.tolerantreader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Date: 12/30/15 - 18:39 PM
 *
 * @author Jeroen Meulemeester
 */

class RainbowFishSerializerTest {

  /**
   * Create a temporary folder, used to generate files in during this test
   */
  @TempDir
  static Path testFolder;

  @BeforeEach
  void beforeEach() {
    assertTrue(Files.isDirectory(testFolder));
  }

  /**
   * Rainbow fish version 1 used during the tests
   */
  private static final RainbowFish V1 = new RainbowFish("version1", 1, 2, 3);

  /**
   * Rainbow fish version 2 used during the tests
   */
  private static final RainbowFishV2 V2 = new RainbowFishV2("version2", 4, 5, 6, true, false, true);

  /**
   * Verify if a fish, written as version 1 can be read back as version 1
   */
  @Test
  void testWriteV1ReadV1() throws Exception {
    final var outputPath = Files.createFile(testFolder.resolve("outputFile"));
    RainbowFishSerializer.writeV1(V1, outputPath.toString());

    final var fish = RainbowFishSerializer.readV1(outputPath.toString());
    assertNotSame(V1, fish);
    assertEquals(V1.getName(), fish.getName());
    assertEquals(V1.getAge(), fish.getAge());
    assertEquals(V1.getLengthMeters(), fish.getLengthMeters());
    assertEquals(V1.getWeightTons(), fish.getWeightTons());

  }

  /**
   * Verify if a fish, written as version 2 can be read back as version 1
   */
  @Test
  void testWriteV2ReadV1() throws Exception {
    final var outputPath = Files.createFile(testFolder.resolve("outputFile2"));
    RainbowFishSerializer.writeV2(V2, outputPath.toString());

    final var fish = RainbowFishSerializer.readV1(outputPath.toString());
    assertNotSame(V2, fish);
    assertEquals(V2.getName(), fish.getName());
    assertEquals(V2.getAge(), fish.getAge());
    assertEquals(V2.getLengthMeters(), fish.getLengthMeters());
    assertEquals(V2.getWeightTons(), fish.getWeightTons());
  }

}
