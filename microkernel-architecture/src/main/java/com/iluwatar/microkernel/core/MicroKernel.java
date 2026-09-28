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
import com.iluwatar.microkernel.registry.PluginRegistry;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/** Manages plugin lifecycle, document state, and plugin-directed transformations. */
@Slf4j
public class MicroKernel {

  private final PluginRegistry registry;
  private final IpcRouter ipcRouter;

  // The kernel now owns the core domain state
  private final StringBuilder documentBuffer;

  /** Creates an empty kernel with its own plugin registry and message router. */
  public MicroKernel() {
    this.registry = new PluginRegistry();
    this.ipcRouter = new IpcRouter(registry);
    this.documentBuffer = new StringBuilder();
  }

  /** Registers a plugin and gives it access to the kernel's message router. */
  public void loadPlugin(Plugin plugin) {
    registry.register(plugin);
    plugin.initialize(ipcRouter);
  }

  /** Starts a registered plugin, if present. */
  public void startPlugin(String pluginName) {
    var plugin = registry.getPlugin(pluginName);
    if (plugin != null) {
      plugin.onStart();
    }
  }

  /** Stops and removes a registered plugin, if present. */
  public void unloadPlugin(String pluginName) {
    var plugin = registry.getPlugin(pluginName);
    if (plugin != null) {
      plugin.onStop();
      registry.deregister(pluginName);
    }
  }

  /** Returns the names of all registered plugins. */
  public List<String> getActivePlugins() {
    return registry.getRegisteredPluginNames();
  }

  /** Appends a line of text to the document. */
  public void addTextToDocument(String text) {
    documentBuffer.append(text).append("\n");
  }

  /** Returns the current document contents. */
  public String readDocument() {
    return documentBuffer.toString();
  }

  /** Removes all text from the document. */
  public void clearDocument() {
    documentBuffer.setLength(0);
  }

  /**
   * Sends the document to a registered plugin and replaces it with a successful transformation.
   *
   * @param pluginName the registered plugin to invoke
   * @return a status message describing the outcome
   */
  public String transformDocumentWithPlugin(String pluginName) {
    if (!registry.getRegisteredPluginNames().contains(pluginName)) {
      return "ERROR: Plugin '" + pluginName + "' is not active";
    }

    // Kernel sends its own document state to the plugin
    Message msg = new Message("Kernel", pluginName, "TRANSFORM", documentBuffer.toString());
    String transformedText = ipcRouter.sendMessage(msg);

    if (transformedText.startsWith("ERROR") || transformedText.startsWith("IGNORED")) {
      return "Transformation failed: " + transformedText;
    }

    // Kernel updates its internal state based on the plugin's response
    clearDocument();
    documentBuffer.append(transformedText);
    return "Success: Document transformed by " + pluginName;
  }
}
