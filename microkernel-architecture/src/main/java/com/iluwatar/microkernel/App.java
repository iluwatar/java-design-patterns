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

package com.iluwatar.microkernel;

import com.iluwatar.microkernel.cli.CommandLineInterface;
import com.iluwatar.microkernel.core.MicroKernel;
import com.iluwatar.microkernel.ipc.Message;
import com.iluwatar.microkernel.plugins.PluginCatalog;
import lombok.extern.slf4j.Slf4j;

/**
 * Demonstrates a Microkernel architecture featuring a Plugin Registry and Inter-Process
 * Communication (IPC).
 *
 * <p>The {@link MicroKernel} delegates all feature capabilities to plugins. Plugins do not interact
 * by calling methods on one another; instead, they pass {@link Message} records through the
 * kernel's IPC router.
 */
@Slf4j
public class App {

  /**
   * Starts the interactive microkernel application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    // 1. Initialize the Core System
    var kernel = new MicroKernel();
    var catalog = new PluginCatalog();

    // 2. Initialize the User Interface, injecting the kernel
    var cli = new CommandLineInterface(kernel, catalog);

    // 3. Start the application loop
    cli.run();
  }
}
