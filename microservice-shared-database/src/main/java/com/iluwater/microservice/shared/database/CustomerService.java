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
public class CustomerService implements ICustomerService{
    private static final String DB_FILE = "microservice-shared-database/etc/localdb.txt";

    private Optional<String[]> findCustomerById(int customerId) {
        File file = new File(DB_FILE);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("CUSTOMERS")) {
                    if(!scanner.nextLine().isEmpty()){
                        while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                            String[] parts = line.split(", ");
                            if (Integer.parseInt(parts[0]) == customerId) {
                                return Optional.of(parts);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found in findCustomerById().");
        }

        return Optional.of(new String[]{"Error getting data of " + customerId + " : customerID not found"});
    }

    private void setCreditLimit(int customerId, double newCreditLimit) {
        try {
            Path path = Paths.get(DB_FILE);
            List<String> lines = new ArrayList<>(Files.readAllLines(path));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("CUSTOMERS")) {
                    i += 2;
                    while (i < lines.size() && !lines.get(i).isEmpty()) {
                        String[] parts = lines.get(i).split(", ");
                        if (Integer.parseInt(parts[0]) == customerId) {
                            parts[1] = String.valueOf(newCreditLimit);
                            lines.set(i, String.join(", ", parts));
                            Files.write(path, lines);
                            return;
                        }
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File not found in setCreditLimit().");
        }
    }
    private String createCustomer(double creditLimit) {
        int newCustomerID = -1;
        try {
            Path path = Paths.get(DB_FILE);
            List<String> lines = new ArrayList<>(Files.readAllLines(path));
            int index = lines.indexOf("CUSTOMERS") + 2;
            while (!lines.get(index).isEmpty()) {
                String[] parts = lines.get(index).split(", ");
                newCustomerID = Integer.parseInt(parts[0]);
                index++;

            }
            newCustomerID++;
            String customerData = String.join(", ", String.valueOf(newCustomerID), String.valueOf(creditLimit));
            lines.add(index, customerData);
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println("Error in createCustomer(): " + e.getMessage());
        }
        return String.valueOf(newCustomerID);
    }

    @Override
    public Optional<String[]> getCustomerById(int customerId) {
        return findCustomerById(customerId);
    }

    @Override
    public void updateCreditLimit(int customerId, double newCreditLimit) {
        setCreditLimit(customerId, newCreditLimit);
    }
    @Override
    public String newCustomer(double creditLimit){ return createCustomer(creditLimit); }
}
