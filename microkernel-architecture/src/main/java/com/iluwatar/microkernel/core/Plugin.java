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

import com.iluwatar.microkernel.ipc.IpcRouter;
import com.iluwatar.microkernel.ipc.Message;

/** Defines the lifecycle and message-handling contract for a microkernel plugin. */
public interface Plugin {

  /** Returns the unique name used to register and address this plugin. */
  String getName();

  /** Returns a human-readable summary of this plugin. */
  String getDescription();

  /**
   * Provides the router plugins may use to communicate through the kernel.
   *
   * @param ipcRouter the kernel's message router
   */
  void initialize(IpcRouter ipcRouter);

  /** Starts the plugin and allocates any required resources. */
  void onStart();

  /** Stops the plugin and releases any allocated resources. */
  void onStop();

  /**
   * Returns whether the plugin is started and can receive messages.
   *
   * @return true if the plugin is started
   */
  boolean isStarted();

  /**
   * Handles a message routed to this plugin.
   *
   * @param message the incoming message
   * @return the plugin's response
   */
  String handleMessage(Message message);
}
