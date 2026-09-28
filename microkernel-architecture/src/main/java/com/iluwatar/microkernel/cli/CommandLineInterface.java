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
package com.iluwatar.microkernel.cli;

import com.iluwatar.microkernel.core.MicroKernel;
import com.iluwatar.microkernel.core.Plugin;
import com.iluwatar.microkernel.plugins.PluginCatalog;
import java.util.List;
import java.util.Scanner;

/** Runs the interactive console interface for the microkernel application. */
public class CommandLineInterface {

  private final MicroKernel kernel;
  private final PluginCatalog catalog;
  private final Scanner scanner;

  /**
   * Creates a command-line interface backed by the given kernel and plugin catalog.
   *
   * @param kernel the application kernel
   * @param catalog the available plugin catalog
   */
  public CommandLineInterface(MicroKernel kernel, PluginCatalog catalog) {
    this.kernel = kernel;
    this.catalog = catalog;
    this.scanner = new Scanner(System.in);
  }

  /** Displays the main menu and handles commands until the user exits. */
  public void run() {
    System.out.println("=== Microkernel IDE Started ===");

    while (true) {
      System.out.println("\n--- Main Menu ---");
      System.out.println("1. List active plugins");
      System.out.println("2. Install a plugin");
      System.out.println("3. Uninstall a plugin");
      System.out.println("4. Open Text Editor");
      System.out.println("5. Exit");
      System.out.print("Select an option (1-5): ");
      String input = scanner.nextLine().trim();
      int choice;
      try {
        choice = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        continue;
      }

      switch (choice) {
        case 1 -> listPlugins();
        case 2 -> showInstallMenu();
        case 3 -> showUninstallMenu();
        case 4 -> openTextEditor();
        case 5 -> {
          System.out.println("Shutting down kernel... Goodbye!");
          return;
        }
        default -> System.out.println("Invalid option. Please choose between 1 and 5.");
      }
    }
  }

  private void showInstallMenu() {
    System.out.println("\n--- Plugin Marketplace ---");

    // 1. Ask the catalog what is available
    List<String> availablePlugins = catalog.getAvailablePlugins();

    for (int i = 0; i < availablePlugins.size(); i++) {
      System.out.println((i + 1) + ". " + availablePlugins.get(i));
    }
    System.out.println((availablePlugins.size() + 1) + ". Cancel");
    System.out.print("Select plugin to install: ");

    try {
      int choice = Integer.parseInt(scanner.nextLine().trim());

      if (choice == availablePlugins.size() + 1) {
        return; // Cancelled
      }

      if (choice > 0 && choice <= availablePlugins.size()) {

        // 2. Ask the catalog to instantiate the selected plugin
        String selected = availablePlugins.get(choice - 1);
        Plugin plugin = catalog.createPlugin(selected);

        // 3. Hand the new plugin over to the Kernel
        if (kernel.getActivePlugins().contains(plugin.getName())) {
          System.out.println("Plugin '" + plugin.getName() + "' is already installed.");
        } else {
          kernel.loadPlugin(plugin);
          kernel.startPlugin(plugin.getName());
          System.out.println("Successfully installed: " + plugin.getName());
        }
      } else {
        System.out.println("Invalid selection.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid input.");
    }
  }

  private void openTextEditor() {
    System.out.println("\n=== Text Editor Mode ===");
    System.out.println("Type your text line by line. Use the following commands to interact:");
    System.out.println("  :view             - View the current document");
    System.out.println("  :clear            - Empty the document");
    System.out.println(
        "  :apply <plugin>   - Dispatch the document to a plugin for transformation");
    System.out.println("  :exit             - Return to the Main Menu");
    System.out.println("-------------------------------------------------------------------------");

    while (true) {
      System.out.print("editor> ");
      String input = scanner.nextLine();

      if (input.startsWith(":exit")) {
        break;
      } else if (input.startsWith(":view")) {
        System.out.println("\n--- Current Document Buffer ---");
        System.out.println(kernel.readDocument());
        System.out.println("-------------------------------");
      } else if (input.startsWith(":clear")) {
        kernel.clearDocument();
        System.out.println("Document cleared.");
      } else if (input.startsWith(":apply ")) {
        String[] parts = input.split(" ", 2);
        if (parts[1].isBlank()) {
          System.out.println("Usage: :apply <plugin_name>");
          continue;
        }
        String result = kernel.transformDocumentWithPlugin(parts[1]);
        System.out.println(result);
      } else {
        kernel.addTextToDocument(input);
      }
    }
  }

  private void listPlugins() {
    List<String> activePlugins = kernel.getActivePlugins();
    if (activePlugins.isEmpty()) {
      System.out.println("No plugins are currently active.");
    } else {
      System.out.println("Active Plugins: " + activePlugins);
    }
  }

  private void showUninstallMenu() {
    // unchanged
    List<String> activePlugins = kernel.getActivePlugins();
    if (activePlugins.isEmpty()) {
      System.out.println("No plugins available to uninstall.");
      return;
    }
    System.out.println("\n--- Uninstall Plugin ---");
    for (int i = 0; i < activePlugins.size(); i++) {
      System.out.println((i + 1) + ". " + activePlugins.get(i));
    }
    System.out.print("Select plugin to uninstall: ");
    try {
      int choice = Integer.parseInt(scanner.nextLine().trim());
      if (choice > 0 && choice <= activePlugins.size()) {
        kernel.unloadPlugin(activePlugins.get(choice - 1));
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid input.");
    }
  }
}
