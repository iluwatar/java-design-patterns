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

/** Provides lifecycle hooks for plugins that run automatically while started. */
public abstract class AbstractLifecyclePlugin implements Plugin {

  protected IpcRouter ipcRouter;
  private boolean isStarted = false;

  /** {@inheritDoc} */
  @Override
  public void initialize(IpcRouter ipcRouter) {
    this.ipcRouter = ipcRouter;
  }

  /** Starts the plugin and invokes its startup hook. */
  @Override
  public final void onStart() {
    this.isStarted = true;
    doStart(); // Call the hook method
  }

  /** Stops the plugin and invokes its shutdown hook. */
  @Override
  public final void onStop() {
    this.isStarted = false;
    doStop(); // Call the hook method
  }

  /** {@inheritDoc} */
  @Override
  public boolean isStarted() {
    return this.isStarted;
  }

  /** Returns a response indicating that this lifecycle plugin ignores messages. */
  @Override
  public String handleMessage(Message message) {
    // Default behavior for lifecycle plugins: ignore text processing messages
    return "IGNORED: Lifecycle plugins typically operate automatically in the background.";
  }

  // --- Hook methods for subclasses ---
  /** Performs plugin-specific startup work. */
  protected abstract void doStart();

  /** Performs plugin-specific shutdown work. */
  protected abstract void doStop();
}
