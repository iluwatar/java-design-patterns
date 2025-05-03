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
package com.iluwatar.execute.around;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** SimpleFileWriterTest */
class SimpleFileWriterTest {

  @TempDir private Path testFolder;

  @Test
  void testWriterNotNull() throws Exception {
    final var temporaryFilePath = Files.createFile(testFolder.resolve("testfile.txt"));
    new SimpleFileWriter(temporaryFilePath.toString(), Assertions::assertNotNull);
  }

  @Test
  void testCreatesNonExistentFile() throws Exception {
    final var nonExistingFilePath = testFolder.resolve("non-existing-file.txt");
    assertFalse(nonExistingFilePath.toFile().exists());

    new SimpleFileWriter(nonExistingFilePath.toString(), Assertions::assertNotNull);
    assertTrue(nonExistingFilePath.toFile().exists());
  }

  @Test
  void testContentsAreWrittenToFile() throws Exception {
    final var testMessage = "Test message";

    final var temporaryFilePath = Files.createFile(testFolder.resolve("testfile.txt"));
    assertTrue(temporaryFilePath.toFile().exists());

    new SimpleFileWriter(temporaryFilePath.toFile().getPath(), writer -> writer.write(testMessage));
    assertTrue(Files.lines(temporaryFilePath.toFile().toPath()).allMatch(testMessage::equals));
  }

  @Test
  @SneakyThrows
  void testRipplesIoExceptionOccurredWhileWriting() throws Exception {
    var message = "Some error";
    final var temporaryFilePath = Files.createFile(testFolder.resolve("testfile.txt"));
    assertThrows(
        IOException.class,
        () ->
            new SimpleFileWriter(
                temporaryFilePath.toString(),
                writer -> {
                  throw new IOException("error");
                }),
        message);
  }
}
