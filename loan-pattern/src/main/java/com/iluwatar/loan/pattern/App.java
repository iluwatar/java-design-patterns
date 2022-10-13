package com.iluwatar.loan.pattern;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Loan Pattern can loan a resource to a user-defined function. It also helps users create
 * a resource which the users can use and loan the resources to the function which would use it
 * and the resource would be destroyed automatically by the lender at the end.
 * The advantages of using this pattern are multifaceted. First, users are not constrained by the function
 * which can use the loaned resource and users can pass any function that they desire.
 * Second, users are not concerned about the creation, destruction of the resource. The lender takes care of it.
 * There are two examples below about the use of loan pattern for a simple file IO operation.
 * One example is about writing data to a file, and the other example is about reading data from a file.
 */
public class App {

    /**
     * The main method for the loan pattern.
     */
    public static void main(String[] args) throws IOException {
        // export data to the given file
        writeFile("loan-pattern/src/main/resources/data.txt");
        // get data from the given file
        readFile("loan-pattern/src/main/resources/data.txt");
    }

    /**
     * a file path is given and write 0 to 9 numbers to the file.
     */
    public static void writeFile(String fileName) throws IOException {
        Lender.writeUsing(fileName,
                new WriteBlock() {
                    public void call(BufferedWriter out) throws IOException {
                        // define what data should be written to the file
                        for (int i = 0; i < 10; i++) {
                            out.append(i + "");
                            out.newLine();
                        }
                    }
                });
    }

    /**
     * a file path is given, read data line by line from the file and print out.
     */
    public static void readFile(String fileName) throws IOException {
        Lender.readUsing(fileName,
                new ReadBlock() {
                    @Override
                    public void call(BufferedReader reader) throws IOException {
                        while (reader.ready()) {
                            // the way how to read the data
                            String s = reader.readLine();
                            // the way how to operate the data
                            System.out.println(s);
                        }
                    }
                });
    }

}