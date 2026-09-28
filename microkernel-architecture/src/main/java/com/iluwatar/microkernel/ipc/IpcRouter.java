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

import com.iluwatar.microkernel.registry.PluginRegistry;
import lombok.extern.slf4j.Slf4j;

/** Routes messages to registered, started plugins and isolates plugin failures. */
@Slf4j
public class IpcRouter {

  private final PluginRegistry registry;

  /**
   * Creates a router backed by the given plugin registry.
   *
   * @param registry the source of registered plugins
   */
  public IpcRouter(PluginRegistry registry) {
    this.registry = registry;
  }

  /**
   * Delivers a message to its registered, started recipient.
   *
   * @param message the message to route
   * @return the recipient's response or an error message when delivery fails
   */
  public String sendMessage(Message message) {
    LOGGER.info(
        "IPC: Routing message from [{}] to [{}] - Action: {}",
        message.sender(),
        message.recipient(),
        message.action());

    var targetPlugin = registry.getPlugin(message.recipient());

    if (targetPlugin == null) {
      LOGGER.warn("IPC Error: Recipient [{}] not found in registry.", message.recipient());
      return "ERROR: Destination Unreachable";
    }

    if (!targetPlugin.isStarted()) {
      LOGGER.warn(
          "IPC Error: Plugin [{}] is stopped and cannot process messages.", message.recipient());
      return "ERROR: Plugin Stopped";
    }

    // FAULT ISOLATION: A crash here will not crash the main application
    try {
      return targetPlugin.handleMessage(message);
    } catch (Exception e) {
      LOGGER.error(
          "IPC Severe: Plugin [{}] crashed during execution! Isolating fault.",
          targetPlugin.getName(),
          e);
      return "ERROR: Plugin Fault Isolated";
    }
  }
}
