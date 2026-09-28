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
package com.iluwatar.microkernel.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.microkernel.core.MicroKernel;
import com.iluwatar.microkernel.plugins.PluginCatalog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class CommandLineInterfaceTest {

  @Test
  void installsListsAndUninstallsPlugin() {
    var kernel = new MicroKernel();
    var output = runCli(kernel, "2\n3\n3\n1\n1\n5\n");

    assertTrue(output.contains("Successfully installed: Uppercase"));
    assertTrue(output.contains("No plugins are currently active."));
    assertTrue(kernel.getActivePlugins().isEmpty());
  }

  @Test
  void installsDuplicateAndUninstallsPlugin() {
    var kernel = new MicroKernel();
    var output = runCli(kernel, "2\n3\n2\n3\n1\n3\n1\n1\n5\n");

    assertTrue(output.contains("Successfully installed: Uppercase"));
    assertTrue(output.contains("Plugin 'Uppercase' is already installed."));
    assertTrue(output.contains("Active Plugins: [Uppercase]"));
    assertTrue(output.contains("No plugins are currently active."));
    assertTrue(kernel.getActivePlugins().isEmpty());
  }

  @Test
  void reportsNoPluginsToUninstall() {
    var output = runCli(new MicroKernel(), "3\n5\n");

    assertTrue(output.contains("No plugins available to uninstall."));
  }

  @Test
  void reportsInvalidInstallAndUninstallSelections() {
    var kernel = new MicroKernel();
    var output =
        runCli(kernel, "2\ninvalid\n2\n0\n2\n5\n2\n6\n2\n3\n3\ninvalid\n3\n0\n3\n2\n3\n1\n5\n");

    assertTrue(output.contains("Invalid input."));
    assertTrue(output.contains("Invalid selection."));
    assertTrue(output.contains("Successfully installed: Uppercase"));
    assertTrue(kernel.getActivePlugins().isEmpty());
  }

  @Test
  void editsAndTransformsDocument() {
    var kernel = new MicroKernel();
    var output = runCli(kernel, "2\n3\n4\nhello world\n:apply Uppercase\n:view\n:exit\n5\n");

    assertTrue(output.contains("Success: Document transformed by Uppercase"));
    assertTrue(output.contains("HELLO WORLD"));
    assertEquals("HELLO WORLD\n", kernel.readDocument());
  }

  @Test
  void clearsDocumentAndReportsUnknownPlugin() {
    var kernel = new MicroKernel();
    var output =
        runCli(
            kernel,
            "2\n3\n4\nold text\n:clear\n:apply \nnew text\n:apply Missing\n:apply Uppercase\n:view\n:exit\n5\n");

    assertTrue(output.contains("Document cleared."));
    assertTrue(output.contains("Usage: :apply <plugin_name>"));
    assertTrue(output.contains("ERROR: Plugin 'Missing' is not active"));
    assertTrue(output.contains("Success: Document transformed by Uppercase"));
    assertTrue(output.contains("NEW TEXT"));
    assertEquals("NEW TEXT\n", kernel.readDocument());
  }

  @Test
  void reportsInvalidMenuInputAndChoice() {
    var output = runCli(new MicroKernel(), "invalid\n9\n5\n");

    assertTrue(output.contains("Invalid input. Please enter a number."));
    assertTrue(output.contains("Invalid option. Please choose between 1 and 5."));
  }

  private static String runCli(MicroKernel kernel, String input) {
    InputStream originalInput = System.in;
    PrintStream originalOutput = System.out;
    var output = new ByteArrayOutputStream();

    try {
      System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
      System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
      new CommandLineInterface(kernel, new PluginCatalog()).run();
    } finally {
      System.setIn(originalInput);
      System.setOut(originalOutput);
    }

    return output.toString(StandardCharsets.UTF_8);
  }
}
