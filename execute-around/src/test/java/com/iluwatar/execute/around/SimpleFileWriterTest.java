/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/12/15 - 3:21 PM
 *
 * @author Jeroen Meulemeester
 */
public class SimpleFileWriterTest {

  /**
   * Create a temporary folder, used to generate files in during this test
   */
  @Rule
  public final TemporaryFolder testFolder = new TemporaryFolder();

  /**
   * Verify if the given writer is not 'null'
   */
  @Test
  public void testWriterNotNull() throws Exception {
    final File temporaryFile = this.testFolder.newFile();
    new SimpleFileWriter(temporaryFile.getPath(), Assert::assertNotNull);
  }

  /**
   * Test if the {@link SimpleFileWriter} creates a file if it doesn't exist
   */
  @Test
  public void testNonExistentFile() throws Exception {
    final File nonExistingFile = new File(this.testFolder.getRoot(), "non-existing-file");
    assertFalse(nonExistingFile.exists());

    new SimpleFileWriter(nonExistingFile.getPath(), Assert::assertNotNull);
    assertTrue(nonExistingFile.exists());
  }

  /**
   * Test if the data written to the file writer actually gets in the file
   */
  @Test
  public void testActualWrite() throws Exception {
    final String testMessage = "Test message";

    final File temporaryFile = this.testFolder.newFile();
    assertTrue(temporaryFile.exists());

    new SimpleFileWriter(temporaryFile.getPath(), writer -> writer.write(testMessage));
    assertTrue(Files.lines(temporaryFile.toPath()).allMatch(testMessage::equals));
  }

  /**
   * Verify if an {@link IOException} during the write ripples through
   */
  @Test(expected = IOException.class)
  public void testIoException() throws Exception {
    final File temporaryFile = this.testFolder.newFile();
    new SimpleFileWriter(temporaryFile.getPath(), writer -> {
      throw new IOException("");
    });
  }

}
