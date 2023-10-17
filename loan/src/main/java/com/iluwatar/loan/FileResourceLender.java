package com.iluwatar.loan;
import java.io.*;
/**
 * A utility class for managing file-based resources, specifically for writing and reading loan-related details.
 * It implements the ResourceLender interface for BufferedWriter.
 */
public class FileResourceLender implements ResourceLender<BufferedWriter> {
    /**
     * Writes data to the specified file using a WriteBlock.
     *
     * @param fileName The name of the file to write to.
     * @param block The writing operation encapsulated as a WriteBlock.
     * @throws IOException If any IO related errors occur.
     */
    @Override
    public void useResource(String fileName, WriteBlock block) throws IOException {
        File csvFile = new File(fileName);
        if (!csvFile.exists()) {
            csvFile.createNewFile();
        }
        FileWriter fw = new FileWriter(csvFile.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        block.call(bufferedWriter);
        bufferedWriter.close();
    }
    /**
     * Reads data from the specified file using a ReadBlock.
     *
     * @param fileName The name of the file to read from.
     * @param block The reading operation encapsulated as a ReadBlock.
     * @throws IOException If any IO related errors occur.
     */
    @Override
    public void readResource(String fileName, ReadBlock block) throws IOException {
        File inputFile = new File(fileName);
        FileReader fileReader = new FileReader(inputFile.getAbsoluteFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        block.call(bufferedReader);
        bufferedReader.close();
    }
    /**
     * Writes column names to the specified file.
     *
     * @param attrName The name of the attribute to be associated with the column names.
     * @param fileName The name of the file to write to.
     * @param colNames An array containing the names of the columns to be written.
     * @throws IOException If any IO related errors occur.
     */
    @Override
    public void writeColumnName(String attrName, String fileName, String[] colNames) throws IOException {
        useResource(fileName, new WriteBlock() {
            public void call(BufferedWriter out) throws IOException {
                StringBuilder buffer = new StringBuilder();
                for (String string : colNames) {
                    buffer.append(string);
                    buffer.append(',');
                }
                out.append(attrName + " = " + buffer.toString());
                out.newLine();
            }
        });
    }
}