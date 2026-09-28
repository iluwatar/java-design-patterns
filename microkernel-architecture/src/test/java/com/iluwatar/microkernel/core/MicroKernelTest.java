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
package com.iluwatar.microkernel.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.microkernel.ipc.Message;
import com.iluwatar.microkernel.plugins.UppercasePlugin;
import org.junit.jupiter.api.Test;

class MicroKernelTest {

  @Test
  void loadsStartsAndUnloadsPlugin() {
    var kernel = new MicroKernel();
    var plugin = new UppercasePlugin();

    kernel.loadPlugin(plugin);
    assertEquals(java.util.List.of("Uppercase"), kernel.getActivePlugins());
    assertFalse(plugin.isStarted());

    kernel.startPlugin(plugin.getName());
    assertTrue(plugin.isStarted());

    kernel.unloadPlugin(plugin.getName());
    assertFalse(plugin.isStarted());
    assertTrue(kernel.getActivePlugins().isEmpty());
  }

  @Test
  void transformsAndReplacesDocument() {
    var kernel = new MicroKernel();
    var plugin = new UppercasePlugin();
    kernel.loadPlugin(plugin);
    kernel.startPlugin(plugin.getName());
    kernel.addTextToDocument("hello microkernel");

    assertEquals(
        "Success: Document transformed by Uppercase",
        kernel.transformDocumentWithPlugin("Uppercase"));
    assertEquals("HELLO MICROKERNEL\n", kernel.readDocument());
  }

  @Test
  void rejectsUnknownOrStoppedPluginWithoutChangingDocument() {
    var kernel = new MicroKernel();
    var plugin = new UppercasePlugin();
    kernel.addTextToDocument("unchanged");

    assertEquals(
        "ERROR: Plugin 'Uppercase' is not active", kernel.transformDocumentWithPlugin("Uppercase"));

    kernel.loadPlugin(plugin);
    assertEquals(
        "Transformation failed: ERROR: Plugin Stopped",
        kernel.transformDocumentWithPlugin("Uppercase"));
    assertEquals("unchanged\n", kernel.readDocument());
  }

  @Test
  void clearsDocumentAndIgnoresUnknownLifecycleRequests() {
    var kernel = new MicroKernel();
    kernel.addTextToDocument("text");

    kernel.startPlugin("missing");
    kernel.unloadPlugin("missing");
    kernel.clearDocument();

    assertEquals("", kernel.readDocument());
    assertTrue(kernel.getActivePlugins().isEmpty());
  }

  @Test
  void routesAPluginResponseThatRepresentsFailureWithoutOverwritingDocument() {
    var kernel = new MicroKernel();
    Plugin plugin =
        new UppercasePlugin() {
          @Override
          public String handleMessage(Message message) {
            return "ERROR: failed";
          }
        };
    kernel.loadPlugin(plugin);
    kernel.startPlugin(plugin.getName());
    kernel.addTextToDocument("preserve me");

    assertEquals(
        "Transformation failed: ERROR: failed",
        kernel.transformDocumentWithPlugin(plugin.getName()));
    assertEquals("preserve me\n", kernel.readDocument());
  }
}
