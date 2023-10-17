package com.iluwatar.loan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileResourceLenderTest {

    private FileResourceLender lender;
    private Path testFile;

    @BeforeEach
    public void setUp() {
        lender = new FileResourceLender();
        testFile = Paths.get("test_loan.txt");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
    }

    @Test
    public void testUseResource() throws IOException {
        lender.useResource(testFile.toString(), new ResourceLender.WriteBlock() {
            @Override
            public void call(BufferedWriter writer) throws IOException {
                writer.write("Test data");
            }
        });

        assertTrue(Files.exists(testFile));
        String content = new String(Files.readAllBytes(testFile));
        assertEquals("Test data", content.trim());
    }

    @Test
    public void testReadResource() throws IOException {
        String sampleText = "Sample read test";
        Files.write(testFile, sampleText.getBytes());

        lender.readResource(testFile.toString(), new ResourceLender.ReadBlock() {
            @Override
            public void call(BufferedReader reader) throws IOException {
                String line = reader.readLine();
                assertEquals(sampleText, line);
            }
        });
    }

    @Test
    public void testWriteColumnName() throws IOException {
        lender.writeColumnName("Loan Details", testFile.toString(), new String[]{"Borrower Name", "Loan Amount"});

        String expected = "Loan Details = Borrower Name,Loan Amount,";
        String content = new String(Files.readAllBytes(testFile));
        assertEquals(expected, content.trim());
    }
}
