package com.iluwater.monolithic.architecture;

import java.util.Scanner;


/**
 * The main application class providing a command-line interface to interact
 * with customer and order modules.
 * <p>
 * The application provides menu-driven options to the user for performing various
 * operations like fetching customer details by ID, updating credit limits,
 * creating new customers, making orders, and fetching order totals for a given customer.
 * </p>
 * <p>
 * The underlying data operations are performed through {@link CustomerModuleInterface} and
 * {@link OrderModuleInterface} implementations.
 * </p>
 */
public class App {

  /**
   * An instance of the customer module for performing customer-related operations.
   */
  private static final CustomerModuleInterface customerModule = new CustomerModule();

  /**
   * An instance of the order module for performing order-related operations.
   */
  private static final OrderModuleInterface orderModule = new OrderModule();

  /**
   * The main entry point of the application.
   *
   * @param args Command-line arguments (not used in the current implementation).
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      displayMenu();
      String choice = scanner.nextLine();

      try {
        switch (choice) {
          case "1":
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String[] customer = customerModule.getCustomerById(customerId);
            System.out.println("Customer Info: " + String.join(", ", customer));
            break;

          case "2":
            System.out.print("Enter Customer ID: ");
            customerId = scanner.nextInt();
            System.out.print("Enter New Credit Limit: ");
            double creditLimit = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            customerModule.updateCreditLimit(customerId, creditLimit);
            System.out.println("Credit Limit Updated Successfully!");
            break;

          case "3":
            System.out.print("Enter Initial Credit Limit: ");
            creditLimit = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            String newCustomerId = customerModule.newCustomer(creditLimit);
            System.out.println("New Customer Created with ID: " + newCustomerId);
            break;

          case "4":
            System.out.print("Enter Customer ID: ");
            customerId = scanner.nextInt();
            System.out.print("Enter Order Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            String orderId = orderModule.makeOrder(customerId, amount);
            System.out.println("Order Created Successfully with ID: " + orderId);
            break;

          case "5":
            System.out.print("Enter Customer ID: ");
            customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String[] orderTotal = orderModule.getOrderTotalByCustomerId(customerId);
            System.out.println("Order Total: " + String.join(", ", orderTotal));
            break;

          case "Q":
            System.out.println("Exiting the application.");
            scanner.close();
            return;

          default:
            System.out.println("Invalid choice. Please select a valid option.");
        }
      } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
      }
    }
  }

  /**
   * Displays the main menu options to the user.
   */
  private static void displayMenu() {
    System.out.println("\nChoose an option:");
    System.out.println("1. Get Customer by ID");
    System.out.println("2. Update Customer Credit Limit");
    System.out.println("3. Create New Customer");
    System.out.println("4. Make an Order");
    System.out.println("5. Get Order Total by Customer ID");
    System.out.println("Q. Quit");
    System.out.print("Your choice: ");
  }

}
