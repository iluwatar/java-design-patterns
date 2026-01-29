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
package com.iluwatar.execute.around

// ABOUTME: Tests for SimpleFileWriter verifying file creation, content writing, and error propagation.
// ABOUTME: Uses temporary directories to isolate test file operations.

import java.io.File
import java.io.IOException
import kotlin.io.path.createTempDirectory
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** SimpleFileWriterTest */
class SimpleFileWriterTest {

    @Test
    fun testWriterNotNull() {
        val tempDir = createTempDirectory("sfwt")
        val temporaryFile = File.createTempFile("test", ".tmp", tempDir.toFile())
        temporaryFile.deleteOnExit()
        SimpleFileWriter(temporaryFile.path) { writer -> assertNotNull(writer) }
    }

    @Test
    fun testCreatesNonExistentFile() {
        val tempDir = createTempDirectory("sfwt")
        val nonExistingFile = File(tempDir.toFile(), "non-existing-file")
        assertFalse(nonExistingFile.exists())

        SimpleFileWriter(nonExistingFile.path) { writer -> assertNotNull(writer) }
        assertTrue(nonExistingFile.exists())
        nonExistingFile.deleteOnExit()
    }

    @Test
    fun testContentsAreWrittenToFile() {
        val testMessage = "Test message"

        val tempDir = createTempDirectory("sfwt")
        val temporaryFile = File.createTempFile("test", ".tmp", tempDir.toFile())
        temporaryFile.deleteOnExit()
        assertTrue(temporaryFile.exists())

        SimpleFileWriter(temporaryFile.path) { writer -> writer.write(testMessage) }
        assertTrue(temporaryFile.readLines().all { it == testMessage })
    }

    @Test
    fun testRipplesIoExceptionOccurredWhileWriting() {
        val tempDir = createTempDirectory("sfwt")
        val temporaryFile = File.createTempFile("test", ".tmp", tempDir.toFile())
        temporaryFile.deleteOnExit()

        assertThrows(IOException::class.java) {
            SimpleFileWriter(temporaryFile.path) {
                throw IOException("error")
            }
        }
    }
}
