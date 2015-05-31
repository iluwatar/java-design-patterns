package com.iluwatar.executearound;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * Interface for specifying what to do with the file resource.
 *
 */
public interface FileWriterAction {

	void writeFile(FileWriter writer) throws IOException;
	
}
