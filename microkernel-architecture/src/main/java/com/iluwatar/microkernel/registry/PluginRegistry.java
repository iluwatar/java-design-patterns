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
package com.iluwatar.microkernel.registry;

import com.iluwatar.microkernel.core.Plugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/** Stores plugins by name for lookup and message routing. */
@Slf4j
public class PluginRegistry {

  private final Map<String, Plugin> plugins = new HashMap<>();

  /** Registers a plugin under its name, replacing any existing plugin with that name. */
  public void register(Plugin plugin) {
    plugins.put(plugin.getName(), plugin);
  }

  /** Removes the plugin registered under the given name, if present. */
  public void deregister(String name) {
    plugins.remove(name);
  }

  /** Returns the plugin registered under the given name, or {@code null} if absent. */
  public Plugin getPlugin(String name) {
    return plugins.get(name);
  }

  /** Returns an immutable snapshot of all currently registered plugin names. */
  public List<String> getRegisteredPluginNames() {
    return plugins.keySet().stream().toList();
  }
}
