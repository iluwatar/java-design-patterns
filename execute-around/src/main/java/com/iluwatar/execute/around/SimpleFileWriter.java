package com.iluwatar.execute.around;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * SimpleFileWriter handles opening and closing file for the user. The user
 * only has to specify what to do with the file resource through {@link FileWriterAction}
 * parameter.
 *
 */
public class SimpleFileWriter {

	public SimpleFileWriter(String filename, FileWriterAction action) throws IOException {
		FileWriter writer = new FileWriter(filename);
		try {
			action.writeFile(writer);
		} finally {
			writer.close();
		}
	}
}
