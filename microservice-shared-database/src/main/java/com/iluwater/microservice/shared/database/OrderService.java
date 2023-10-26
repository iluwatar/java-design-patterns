package com.iluwater.microservice.shared.database;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class OrderService implements IOrderService{
    private static final String DB_FILE = "microservice-shared-database/etc/localdb.txt";

    private Optional<String[]> findOrderTotalByCustomerId(int customerId) {
        try (Scanner scanner = new Scanner(new File(DB_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("ORDERS")) {
                    if (!scanner.nextLine().isEmpty()){
                        while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                            String[] parts = line.split(", ");
                            if (Integer.parseInt(parts[1]) == customerId) {
                                return Optional.of(parts);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found in findOrderTotalByCustomerId().");
        }
        return Optional.of(new String[]{"Error getting total orders of " + customerId + " : orderID not found"});
    }

    private String createOrder(int customerId, double amount) throws Exception {
        int newOrderID = -1;
        CustomerService customerService = new CustomerService();
        Optional<String[]> customer = customerService.getCustomerById(customerId);
        Optional<String[]> order = findOrderTotalByCustomerId(customerId);

        if (customer.isPresent() && order.isPresent()) {
            double creditLimit = Double.parseDouble(customer.get()[1]);
            double orderTotal = Double.parseDouble(order.get()[3]);

            if (orderTotal + amount <= creditLimit) {
                newOrderID =
                        insertOrder(List.of(new String[]{
                        String.valueOf(customerId),
                        "ACCEPTED",
                        String.valueOf(amount)
                }));
            } else {
                // Handle credit limit exceeded
                throw new Exception("Exceed the CREDIT_LIMIT.");
            }
        } else {
            // Handle customer not found or other issues
            throw new Exception("Customer not found.");
        }
        return String.valueOf(newOrderID);
    }

    private int insertOrder(List<String> order) {
        int orderID = -1;
        try {
            Path path = Paths.get(DB_FILE);
            List<String> lines = new ArrayList<>(Files.readAllLines(path));
            int index = lines.indexOf("ORDERS") + 2;
            while (index < lines.size()) {
                String[] parts = lines.get(index).split(", ");
                orderID = Integer.parseInt(parts[0]);
                index++;
            }
            orderID++;
            order.add(0, String.valueOf(orderID));
            lines.add(index, String.join(", ", order));
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println("File not found in insertOrder().");
        }
        return orderID;
    }

    @Override
    public String makeOrder(int customerId, double amount) throws Exception {
        return createOrder(customerId, amount);
    }
    @Override
    public Optional<String[]> getOrderTotalByCustomerId(int customerId){ return findOrderTotalByCustomerId(customerId); }


}
