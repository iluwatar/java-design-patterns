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
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * The Module pattern can be considered a Creational pattern and a Structural
 * pattern. It manages the creation and organization of other elements, and
 * groups them as the structural pattern does. An object that applies this
 * pattern can provide the equivalent of a namespace, providing the
 * initialization and finalization process of a static class or a class with
 * static members with cleaner, more concise syntax and semantics.
 * <p>
 * The below example demonstrates a JUnit test for testing two different
 * modules: File Logger and Console Logger
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
	public void positiveTestConsoleMessage() throws IOException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FileLoggerModule fileLoggerModule = FileLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		fileLoggerModule.prepare();

		/* Print 'Message' in file */
		fileLoggerModule.printString(MESSAGE);

		/* Test if 'Message' is printed on console */
		assertEquals(readFirstLine(), MESSAGE);

		/* Unprepare to cleanup the modules */
		fileLoggerModule.unprepare();
	}

	/**
	 * This test verify that nothing is printed in output file
	 * 
	 * @throws IOException
	 */
	@Test
	public void negativeTestConsoleMessage() throws IOException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final ConsoleLoggerModule consoleLoggerModule = ConsoleLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		consoleLoggerModule.prepare();

		/* Test if nothing is printed on console */
		assertEquals(readFirstLine(), null);

		/* Unprepare to cleanup the modules */
		consoleLoggerModule.unprepare();
	}

	/**
	 * This test verify that 'ERROR' is perfectly printed in error file
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void positiveTestConsoleErrorMessage() {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final ConsoleLoggerModule consoleLoggerModule = ConsoleLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		consoleLoggerModule.prepare();

		/* Print 'Error' in file */
		consoleLoggerModule.printErrorString(ERROR);

		/* Test if 'Message' is printed on console */
		assertEquals(readFirstLine(), ERROR);

		/* Unprepare to cleanup the modules */
		consoleLoggerModule.unprepare();
	}

	/**
	 * This test verify that nothing is printed in error file
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void negativeTestConsoleErrorMessage() {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final ConsoleLoggerModule consoleLoggerModule = ConsoleLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		consoleLoggerModule.prepare();

		/* Test if nothing is printed on console */
		assertEquals(readFirstLine(), null);

		/* Unprepare to cleanup the modules */
		consoleLoggerModule.unprepare();
	}

	/**
	 * This test verify that 'MESSAGE' is perfectly printed in output file
	 * 
	 * @throws IOException
	 */
	@Test
	public void positiveTestFileMessage() throws IOException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FileLoggerModule fileLoggerModule = FileLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		fileLoggerModule.prepare();

		/* Print 'Message' in file */
		fileLoggerModule.printString(MESSAGE);

		/* Test if 'Message' is printed in file */
		assertEquals(readFirstLine(OUTPUT_FILE), MESSAGE);

		/* Unprepare to cleanup the modules */
		fileLoggerModule.unprepare();
	}

	/**
	 * This test verify that nothing is printed in output file
	 * 
	 * @throws IOException
	 */
	@Test
	public void negativeTestFileMessage() throws IOException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FileLoggerModule fileLoggerModule = FileLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		fileLoggerModule.prepare();

		/* Test if nothing is printed in file */
		assertEquals(readFirstLine(OUTPUT_FILE), null);

		/* Unprepare to cleanup the modules */
		fileLoggerModule.unprepare();
	}

	/**
	 * This test verify that 'ERROR' is perfectly printed in error file
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void positiveTestFileErrorMessage() throws FileNotFoundException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FileLoggerModule fileLoggerModule = FileLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		fileLoggerModule.prepare();

		/* Print 'Error' in file */
		fileLoggerModule.printErrorString(ERROR);

		/* Test if 'Message' is printed in file */
		assertEquals(readFirstLine(ERROR_FILE), ERROR);

		/* Unprepare to cleanup the modules */
		fileLoggerModule.unprepare();
	}

	/**
	 * This test verify that nothing is printed in error file
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void negativeTestFileErrorMessage() throws FileNotFoundException {

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		final FileLoggerModule fileLoggerModule = FileLoggerModule
				.getSingleton();

		/* Prepare the essential sub modules, to perform the sequence of jobs */
		fileLoggerModule.prepare();

		/* Test if nothing is printed in file */
		assertEquals(readFirstLine(ERROR_FILE), null);

		/* Unprepare to cleanup the modules */
		fileLoggerModule.unprepare();
	}

	/**
	 * Utility method to read first line of a file
	 * 
	 * @param file
	 * @return
	 */
	private static final String readFirstLine() {

		String firstLine = null;
		BufferedReader bufferedReader = null;
		try {

			/* Create a buffered reader */
			bufferedReader = new BufferedReader(
					new InputStreamReader(System.in));

			/* Read the line */
			firstLine = bufferedReader.readLine();

			logger.info("ModuleTest::readFirstLineFromConsole() : firstLine : "
					+ firstLine);

		} catch (final IOException e) {
			logger.error("ModuleTest::readFirstLineFromConsole()", e);
		} finally {

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (final IOException e) {
					logger.error("ModuleTest::readFirstLineFromConsole()", e);
				}
			}
		}

		return firstLine;
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

			logger.info("ModuleTest::readFirstLine() : firstLine : "
					+ firstLine);

		} catch (final IOException e) {
			logger.error("ModuleTest::readFirstLine()", e);
		} finally {

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (final IOException e) {
					logger.error("ModuleTest::readFirstLine()", e);
				}
			}
		}

		return firstLine;
	}
}
