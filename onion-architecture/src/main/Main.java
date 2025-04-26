import application.TodoService;
import domain.TodoItem;
import infrastructure.TodoRepositoryImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TodoService service = new TodoService(new TodoRepositoryImpl());

        System.out.println("Welcome to the TODO App!");

        boolean running = true;
        while (running) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("[a] Add tasks");
            System.out.println("[d] Mark tasks as done");
            System.out.println("[v] View tasks");
            System.out.println("[q] Quit");
            System.out.print("> ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println("Enter your tasks (type 'q' to stop adding):");
                    while (true) {
                        System.out.print("> ");
                        String input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("q")) break;
                        if (!input.isEmpty()) {
                            service.createTodo(input);
                        }
                    }
                    break;

                case "d":
                    if (service.getTodos().isEmpty()) {
                        System.out.println("📝 No tasks to mark as done.");
                        break;
                    }
                    System.out.println("\nEnter the ID(s) of tasks to mark as done (comma separated, or 'q' to cancel):");
                    System.out.print("> ");
                    String idsInput = scanner.nextLine().trim();
                    if (!idsInput.equalsIgnoreCase("q")) {
                        String[] parts = idsInput.split(",");
                        for (String part : parts) {
                            try {
                                int id = Integer.parseInt(part.trim());
                                service.completeTodo(id);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid ID: " + part);
                            }
                        }
                    }
                    break;

                case "v":
                    System.out.println("\n📋 Your Todo List:");
                    if (service.getTodos().isEmpty()) {
                        System.out.println("No tasks yet!");
                    } else {
                        for (TodoItem item : service.getTodos()) {
                            String status = item.isCompleted() ? "✅ Done" : "❌ Not Done";
                            System.out.println(item.getId() + ": " + item.getTitle() + " [" + status + "]");
                        }
                    }
                    break;

                case "q":
                    running = false;
                    break;

                default:
                    System.out.println("Unknown option. Please try again.");
            }
        }

        System.out.println("\nGoodbye! Here's your final Todo List:");
        
        if(service.getTodos().size() == 0)
            System.out.println("No tasks left!");
        
        else {
            for (TodoItem item : service.getTodos()) {
                String status = item.isCompleted() ? "✅ Done" : "❌ Not Done";
                System.out.println(item.getId() + ": " + item.getTitle() + " [" + status + "]");
            }
        }

        scanner.close();
    }
}
