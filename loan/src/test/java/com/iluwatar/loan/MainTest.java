package com.iluwatar.loan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private Path testFilePath;

    @BeforeEach
    public void setUp() {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);
        testFilePath = Paths.get("./src/main/java/com/iluwatar/loan/loan.txt");
        System.out.println(testFilePath);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    public void testMainMethod() throws IOException {
        Main.main(new String[]{});

        assertTrue(Files.exists(testFilePath), "File should exist after main method is executed.");

        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath.toFile()))) {
            List<String> lines = Files.readAllLines(testFilePath);
            assertTrue(lines.contains("Loan Details = Borrower Name,Loan Amount,Annual Interest Rate,Loan Duration (months),Monthly Payment,Start Date,"), "Column names should be written.");
            assertTrue(lines.contains("John Doe, $10,000, 5%, 24, $444.24, 01-01-2023"), "John Doe's loan details should be written.");
            assertTrue(lines.contains("Alice Smith, $5,000, 4.5%, 12, $421.65, 15-02-2023"), "Alice Smith's loan details should be written.");
        }
    }
}
