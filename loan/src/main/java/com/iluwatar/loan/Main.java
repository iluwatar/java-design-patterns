package com.iluwatar.loan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Main {
    /**
     * Writes loan details to the specified file and then reads them back.
     *
     * <p>The method first writes details like Borrower Name, Loan Amount, etc.,
     * to a specified file. After writing the details, it reads the same file
     * to retrieve and print the entered details.</p>
     *
     * @param args Command line arguments (not used in this method).
     */
    public static void main(String[] args) {

        // Initialize a FileResourceLender to manage loan file operations.
        FileResourceLender lender = new FileResourceLender();

        try {
        // Write specific loan details for two borrowers to the file.
            lender.writeColumnName("Loan Details", "./loan/src/main/java/com/iluwatar/loan/loan.txt", new String[]{"Borrower Name", "Loan Amount", "Annual Interest Rate", "Loan Duration (months)", "Monthly Payment", "Start Date"});
            lender.useResource("./loan/src/main/java/com/iluwatar/loan/loan.txt", new ResourceLender.WriteBlock() {
                @Override
                public void call(BufferedWriter writer) throws IOException {
                    writer.write("John Doe, $10,000, 5%, 24, $444.24, 01-01-2023");
                    writer.newLine();
                    writer.write("Alice Smith, $5,000, 4.5%, 12, $421.65, 15-02-2023");
                    writer.newLine();
                }
            });
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            // Attempt to read and print loan details from the specified file.
            lender.readResource("./loan/src/main/java/com/iluwatar/loan/loan.txt", new ResourceLender.ReadBlock() {
                @Override
                public void call(BufferedReader reader) throws IOException {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
