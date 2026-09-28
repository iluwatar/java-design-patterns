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
package com.iluwatar.microkernel.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.microkernel.core.AbstractLifecyclePlugin;
import com.iluwatar.microkernel.ipc.IpcRouter;
import com.iluwatar.microkernel.ipc.Message;
import com.iluwatar.microkernel.registry.PluginRegistry;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class PluginImplementationsTest {

  @Test
  void uppercasePluginTransformsOnlyTransformActions() {
    var plugin = new UppercasePlugin();

    assertEquals("HELLO", plugin.handleMessage(message("TRANSFORM", "hello")));
    assertEquals("ERROR: Action not supported.", plugin.handleMessage(message("OTHER", "hello")));
    assertEquals("Uppercase", plugin.getName());
    assertFalse(plugin.isStarted());
  }

  @Test
  void removeSpacesPluginRemovesAllWhitespace() {
    var plugin = new RemoveSpacesPlugin();

    assertEquals("onetwo", plugin.handleMessage(message("TRANSFORM", "one \t two")));
    assertEquals("ERROR: Action not supported.", plugin.handleMessage(message("OTHER", "a b")));
  }

  @Test
  void javaLanguagePluginFormatsTransformPayload() {
    var plugin = new JavaLanguagePlugin();

    assertEquals(
        "class A {\n    int x;\n    \n}",
        plugin.handleMessage(message("TRANSFORM", "class A {int x;}")));
    assertEquals("ERROR: Action not supported.", plugin.handleMessage(message("OTHER", "text")));
  }

  @Test
  void lifecyclePluginRunsHooksAndIgnoresMessages() {
    var plugin = new NeonThemePlugin();
    var output = new ByteArrayOutputStream();
    PrintStream originalOutput = System.out;

    try {
      System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
      plugin.onStart();
      assertTrue(plugin.isStarted());
      assertEquals(
          "IGNORED: Lifecycle plugins typically operate automatically in the background.",
          plugin.handleMessage(message("TRANSFORM", "text")));
      plugin.onStop();
      assertFalse(plugin.isStarted());
    } finally {
      System.setOut(originalOutput);
    }

    assertTrue(output.toString(StandardCharsets.UTF_8).contains("\u001B[32m"));
    assertTrue(output.toString(StandardCharsets.UTF_8).contains("\u001B[0m"));
  }

  @Test
  void lifecyclePluginStoresIpcRouterDuringInitialization() {
    var plugin = new TestLifecyclePlugin();
    var router = new IpcRouter(new PluginRegistry());

    plugin.initialize(router);

    assertSame(router, plugin.getIpcRouter());
  }

  private static Message message(String action, String payload) {
    return new Message("test", "plugin", action, payload);
  }

  private static class TestLifecyclePlugin extends AbstractLifecyclePlugin {

    @Override
    public String getName() {
      return "Test";
    }

    @Override
    public String getDescription() {
      return "Test lifecycle plugin";
    }

    @Override
    protected void doStart() {}

    @Override
    protected void doStop() {}

    private IpcRouter getIpcRouter() {
      return ipcRouter;
    }
  }
}
