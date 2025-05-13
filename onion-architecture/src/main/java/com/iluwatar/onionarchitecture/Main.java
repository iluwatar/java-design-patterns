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

/*
 * Onion Architecture is a software design pattern that aims to promote separation of concerns by organizing code into layers that circle around a central core. The core contains the business logic, and the outer layers contain infrastructure concerns like database access, UI, and external services.
 * 
 * 1. Domain Layer (Core) - The TodoItem class is the core domain model of the application. It represents a Todo entity and encapsulates properties like id, title, and isCompleted. This class contains the business logic related to the TodoItem, such as the method markCompleted(), which changes the state of a Todo. This class is purely business logic, and it doesn't depend on any infrastructure components like a database or UI. It’s the heart of the application.
 * 2. Application Layer - The TodoService class orchestrates the use cases of the application, like creating, retrieving, completing, and deleting todos. This is the service layer responsible for the application logic.
 * 3. Infrastructure Layer - The TodoRepositoryImpl is the implementation of the TodoRepository interface, which interacts with the infrastructure (in this case, an in-memory list) to persist the data. It implements the CRUD operations (add, getAll, markAsCompleted, delete) for TodoItem objects.
 */

package com.iluwatar.onionarchitecture;

import com.iluwatar.onionarchitecture.application.TodoService;
import com.iluwatar.onionarchitecture.domain.TodoItem;
import com.iluwatar.onionarchitecture.infrastructure.TodoRepositoryImpl;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    TodoService service = new TodoService(new TodoRepositoryImpl());

    LOGGER.info("Welcome to the TODO App!");

    boolean running = true;
    while (running) {
      LOGGER.info("\nWhat would you like to do?");
      LOGGER.info("[a] Add tasks");
      LOGGER.info("[m] Mark tasks as done");
      LOGGER.info("[v] View tasks");
      LOGGER.info("[d] Delete tasks");
      LOGGER.info("[q] Quit");
      System.out.println("> ");
      String choice = scanner.nextLine().trim().toLowerCase();

      switch (choice) {
        case "a":
          LOGGER.info("Enter your tasks (type 'q' to stop adding):");
          while (true) {
            System.out.println("> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) break;
            if (!input.isEmpty()) {
              service.createTodo(input);
              LOGGER.debug("Task added: {}", input);
            }
          }
          break;

        case "m":
          if (service.getTodos().isEmpty()) {
            LOGGER.info("📝 No tasks to mark as done.");
            break;
          }
          LOGGER.info(
              "\nEnter the ID(s) of tasks to mark as done (comma separated, or 'q' to cancel):");
          System.out.println("> ");
          String idsInput = scanner.nextLine().trim();
          if (!idsInput.equalsIgnoreCase("q")) {
            String[] parts = idsInput.split(",");
            for (String part : parts) {
              try {
                int id = Integer.parseInt(part.trim());
                service.completeTodo(id);
                LOGGER.info("Task with ID {} marked as done.", id);
              } catch (NumberFormatException e) {
                LOGGER.warn("Invalid ID: {}", part);
              }
            }
          }
          break;

        case "v":
          LOGGER.info("\n📋 Your Todo List:");
          if (service.getTodos().isEmpty()) {
            LOGGER.info("No tasks yet!");
          } else {
            for (TodoItem item : service.getTodos()) {
              String status = item.isCompleted() ? "✅ Done" : "❌ Not Done";
              LOGGER.info("{}: {} [{}]", item.getId(), item.getTitle(), status);
            }
          }
          break;

        case "d":
          if (service.getTodos().isEmpty()) {
            LOGGER.info("📝 No tasks to delete.");
            break;
          }
          LOGGER.info("\nEnter the ID(s) of tasks to delete (comma separated, or 'q' to cancel):");
          System.out.println("> ");
          String idsInputForDelete = scanner.nextLine().trim();
          if (!idsInputForDelete.equalsIgnoreCase("q")) {
            String[] parts = idsInputForDelete.split(",");
            for (String part : parts) {
              try {
                int id = Integer.parseInt(part.trim());
                service.delete(id);
                LOGGER.info("Task with ID {} deleted.", id);
              } catch (NumberFormatException e) {
                LOGGER.warn("Invalid ID: {}", part);
              }
            }
          }
          break;

        case "q":
          running = false;
          break;

        default:
          LOGGER.warn("Unknown option. Please try again.");
      }
    }

    LOGGER.info("\nGoodbye! Here's your final Todo List:");

    if (service.getTodos().size() == 0) LOGGER.info("No tasks left!");
    else {
      for (TodoItem item : service.getTodos()) {
        String status = item.isCompleted() ? "✅ Done" : "❌ Not Done";
        LOGGER.info("{}: {} [{}]", item.getId(), item.getTitle(), status);
      }
    }

    scanner.close();
  }
}
