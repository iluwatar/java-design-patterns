/**
 * The MIT License Copyright (c) 2016 Amit Dixit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.module;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * The Data Mapper (DM) is a layer of software that separates the in-memory
 * objects from the database. Its responsibility is to transfer data between the
 * two and also to isolate them from each other. With Data Mapper the in-memory
 * objects needn't know even that there's a database present; they need no SQL
 * interface code, and certainly no knowledge of the database schema. (The
 * database schema is always ignorant of the objects that use it.) Since it's a
 * form of Mapper , Data Mapper itself is even unknown to the domain layer.
 * <p>
 */
public class ModuleTest {

	private static final Logger logger = Logger.getLogger(ModuleTest.class);

	private static final String OUTPUT_FILE = "output.txt";
	private static final String ERROR_FILE = "error.txt";

	private static final String MESSAGE = "MESSAGE";
	private static final String ERROR = "ERROR";

	/**
	 * This test verify that 'MESSAGE' is perfectly printed in output file
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPositiveMessage() throws IOException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FilePrinterModule filePrinterModule = FilePrinterModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		filePrinterModule.prepare(OUTPUT_FILE, ERROR_FILE);

		/* Print 'Message' in file */
		filePrinterModule.printString(MESSAGE);

		/* Test if 'Message' is printed in file */
		assertEquals(readFirstLine(OUTPUT_FILE), MESSAGE);

		/* Unprepare to cleanup the modules */
		filePrinterModule.unprepare();
	}

	/**
	 * This test verify that nothing is printed in output file
	 * 
	 * @throws IOException
	 */
	@Test
	public void testNegativeMessage() throws IOException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FilePrinterModule filePrinterModule = FilePrinterModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		filePrinterModule.prepare(OUTPUT_FILE, ERROR_FILE);

		/* Test if nothing is printed in file */
		assertEquals(readFirstLine(OUTPUT_FILE), null);

		/* Unprepare to cleanup the modules */
		filePrinterModule.unprepare();
	}

	/**
	 * This test verify that 'ERROR' is perfectly printed in error file
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void testPositiveErrorMessage() throws FileNotFoundException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FilePrinterModule filePrinterModule = FilePrinterModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		filePrinterModule.prepare(OUTPUT_FILE, ERROR_FILE);

		/* Print 'Error' in file */
		filePrinterModule.printErrorString(ERROR);

		/* Test if 'Message' is printed in file */
		assertEquals(readFirstLine(ERROR_FILE), ERROR);

		/* Unprepare to cleanup the modules */
		filePrinterModule.unprepare();
	}

	/**
	 * This test verify that nothing is printed in error file
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void testNegativeErrorMessage() throws FileNotFoundException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FilePrinterModule filePrinterModule = FilePrinterModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		filePrinterModule.prepare(OUTPUT_FILE, ERROR_FILE);

		/* Test if nothing is printed in file */
		assertEquals(readFirstLine(ERROR_FILE), null);

		/* Unprepare to cleanup the modules */
		filePrinterModule.unprepare();
	}

	/**
	 * Utility method to read first line of a file
	 * 
	 * @param file
	 * @return
	 */
	private static final String readFirstLine(final String file) {

		String firstLine = null;
		BufferedReader bufferedReader = null;
		try {

			/* Create a buffered reader */
			bufferedReader = new BufferedReader(new FileReader(file));

			/* Read the line */
			firstLine = bufferedReader.readLine();

			logger.info("ModuleTest::readFile() : firstLine : " + firstLine);

		} catch (final IOException e) {
			logger.error("ModuleTest::readFile()", e);
		} finally {

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (final IOException e) {
					logger.error("ModuleTest::readFile()", e);
				}
			}
		}

		return firstLine;
	}
}
