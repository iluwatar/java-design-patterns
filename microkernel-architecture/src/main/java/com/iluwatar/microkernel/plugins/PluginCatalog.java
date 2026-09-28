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

import com.iluwatar.microkernel.core.Plugin;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/** Provides metadata and factories for the plugins available to the application. */
public class PluginCatalog {

  private final Map<String, Supplier<Plugin>> catalog = new LinkedHashMap<>();
  private final List<String> pluginDisplay = new ArrayList<>();

  /** Creates the catalog with the module's built-in plugins. */
  public PluginCatalog() {
    register(JavaLanguagePlugin::new);
    register(NeonThemePlugin::new);
    register(UppercasePlugin::new);
    register(RemoveSpacesPlugin::new);
  }

  /** Helper method to extract the description from the plugin itself. */
  private void register(Supplier<Plugin> factory) {
    // Briefly instantiate the plugin to extract its metadata
    Plugin metadataInstance = factory.get();

    // Store it in the map using its own self-reported description as the key
    catalog.put(metadataInstance.getName(), factory);

    pluginDisplay.add(metadataInstance.getName() + ": " + metadataInstance.getDescription());
  }

  /** Returns display labels for all available plugins. */
  public List<String> getAvailablePlugins() {
    return pluginDisplay;
  }

  /**
   * Creates a fresh plugin matching a catalog display label.
   *
   * @param description the plugin name or its displayed name-and-description label
   * @return a new plugin instance
   * @throws IllegalArgumentException if the label does not identify a catalog plugin
   */
  public Plugin createPlugin(String description) {
    Supplier<Plugin> factory = catalog.get(description.split(":")[0]);
    if (factory != null) {
      return factory.get();
    }
    throw new IllegalArgumentException("Plugin not found in catalog: " + description);
  }
}
