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
package com.iluwatar.microkernel.ipc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.microkernel.core.AbstractOnDemandPlugin;
import com.iluwatar.microkernel.registry.PluginRegistry;
import org.junit.jupiter.api.Test;

class IpcRouterTest {

  private final PluginRegistry registry = new PluginRegistry();
  private final IpcRouter router = new IpcRouter(registry);

  @Test
  void reportsMissingRecipient() {
    assertEquals(
        "ERROR: Destination Unreachable",
        router.sendMessage(new Message("sender", "missing", "ACT", "payload")));
  }

  @Test
  void reportsStoppedRecipient() {
    registry.register(new TestPlugin(false, false));

    assertEquals(
        "ERROR: Plugin Stopped",
        router.sendMessage(new Message("sender", "test", "ACT", "payload")));
  }

  @Test
  void deliversMessageToStartedRecipient() {
    registry.register(new TestPlugin(true, false));

    assertEquals(
        "response:payload", router.sendMessage(new Message("sender", "test", "ACT", "payload")));
  }

  @Test
  void isolatesPluginFailure() {
    registry.register(new TestPlugin(true, true));

    assertEquals(
        "ERROR: Plugin Fault Isolated",
        router.sendMessage(new Message("sender", "test", "ACT", "payload")));
  }

  private static class TestPlugin extends AbstractOnDemandPlugin {

    private final boolean started;
    private final boolean fail;

    private TestPlugin(boolean started, boolean fail) {
      this.started = started;
      this.fail = fail;
    }

    @Override
    public String getName() {
      return "test";
    }

    @Override
    public String getDescription() {
      return "test plugin";
    }

    @Override
    public boolean isStarted() {
      return started;
    }

    @Override
    public String handleMessage(Message message) {
      if (fail) {
        throw new IllegalStateException("test failure");
      }
      return "response:" + message.payload();
    }
  }
}
