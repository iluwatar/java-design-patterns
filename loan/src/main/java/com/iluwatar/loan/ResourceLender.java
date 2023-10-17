package com.iluwatar.loan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface ResourceLender<T> {
    /**
     * Use the resource for writing purposes.
     *
     * @param resourceIdentifier The identifier (e.g., path) of the resource.
     * @param writer The writing operation encapsulated as a WriteBlock.
     * @throws IOException If any IO related errors occur.
     */
    void useResource(String resourceIdentifier, WriteBlock writer) throws IOException;
    /**
     * Reads data from the specified resource.
     *
     * @param resourceIdentifier The identifier (e.g., path) of the resource.
     * @param reader The reading operation encapsulated as a ReadBlock.
     * @throws IOException If any IO related errors occur.
     */
    void readResource(String resourceIdentifier, ReadBlock reader) throws IOException;
    /**
     * Writes column names to the specified resource.
     *
     * @param columnName The name of the attribute/column to be associated with the column names.
     * @param fileName The name of the file to write to.
     * @param columnNames An array containing the names of the columns to be written.
     * @throws IOException If any IO related errors occur.
     */
    void writeColumnName(String columnName, String fileName, String[] columnNames) throws IOException;
    /**
     * Represents a block of writing operations that use a BufferedWriter.
     */
    interface WriteBlock {
        /**
         * Executes the block of writing operations.
         *
         * @param writer The BufferedWriter instance to be used for writing.
         * @throws IOException If any IO related errors occur.
         */
        void call(BufferedWriter writer) throws IOException;
    }
    /**
     * Represents a block of reading operations that use a BufferedReader.
     */
    interface ReadBlock {
        /**
         * Executes the block of reading operations.
         *
         * @param reader The BufferedReader instance to be used for reading.
         * @throws IOException If any IO related errors occur.
         */
        void call(BufferedReader reader) throws IOException;
    }
}
