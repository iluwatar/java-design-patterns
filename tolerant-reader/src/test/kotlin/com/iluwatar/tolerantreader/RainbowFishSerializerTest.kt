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
package com.iluwatar.tolerantreader

// ABOUTME: Tests for RainbowFishSerializer verifying the Tolerant Reader pattern.
// ABOUTME: Validates that V1 reader can deserialize both V1 and V2 serialized data correctly.

import java.nio.file.Files
import java.nio.file.Path
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

/** RainbowFishSerializerTest */
class RainbowFishSerializerTest {

    /** Create a temporary folder, used to generate files in during this test */
    companion object {
        @TempDir
        @JvmStatic
        lateinit var testFolder: Path

        /** Rainbow fish version 1 used during the tests */
        private val V1 = RainbowFish("version1", 1, 2, 3)

        /** Rainbow fish version 2 used during the tests */
        private val V2 = RainbowFishV2("version2", 4, 5, 6, sleeping = true, hungry = false, angry = true)
    }

    @BeforeEach
    fun beforeEach() {
        assertTrue(Files.isDirectory(testFolder))
    }

    /** Verify if a fish, written as version 1 can be read back as version 1 */
    @Test
    fun testWriteV1ReadV1() {
        val outputPath = Files.createFile(testFolder.resolve("outputFile"))
        RainbowFishSerializer.writeV1(V1, outputPath.toString())

        val fish = RainbowFishSerializer.readV1(outputPath.toString())
        assertNotSame(V1, fish)
        assertEquals(V1.name, fish.name)
        assertEquals(V1.age, fish.age)
        assertEquals(V1.lengthMeters, fish.lengthMeters)
        assertEquals(V1.weightTons, fish.weightTons)
    }

    /** Verify if a fish, written as version 2 can be read back as version 1 */
    @Test
    fun testWriteV2ReadV1() {
        val outputPath = Files.createFile(testFolder.resolve("outputFile2"))
        RainbowFishSerializer.writeV2(V2, outputPath.toString())

        val fish = RainbowFishSerializer.readV1(outputPath.toString())
        assertNotSame(V2, fish)
        assertEquals(V2.name, fish.name)
        assertEquals(V2.age, fish.age)
        assertEquals(V2.lengthMeters, fish.lengthMeters)
        assertEquals(V2.weightTons, fish.weightTons)
    }
}
