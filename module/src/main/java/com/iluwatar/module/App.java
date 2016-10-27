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

import java.io.FileNotFoundException;

/**
 * The Data Mapper (DM) is a layer of software that separates the in-memory
 * objects from the database. Its responsibility is to transfer data between the
 * two and also to isolate them from each other. With Data Mapper the in-memory
 * objects needn't know even that there's a database present; they need no SQL
 * interface code, and certainly no knowledge of the database schema. (The
 * database schema is always ignorant of the objects that use it.) Since it's a
 * form of Mapper , Data Mapper itself is even unknown to the domain layer.
 * <p>
 * The below example demonstrates basic CRUD operations: Create, Read, Update,
 * and Delete.
 * 
 */
public final class App {

	public static FileLoggerModule fileLoggerModule = null;
	public static ConsoleLoggerModule consoleLoggerModule = null;

	public static void prepare() throws FileNotFoundException {
		
		fileLoggerModule = FileLoggerModule.getSingleton();
		consoleLoggerModule = ConsoleLoggerModule.getSingleton();

		/* Prepare modules */
		fileLoggerModule.prepare();
		consoleLoggerModule.prepare();
	}

	public static void unprepare() {

		/* Close all resources */
		fileLoggerModule.unprepare();
		consoleLoggerModule.unprepare();
	}

	public static final void execute(final String... args) {

		/* Send logs on file system */
		fileLoggerModule.printString("Message");
		fileLoggerModule.printErrorString("Error");

		/* Send logs on console */
		consoleLoggerModule.printString("Message");
		consoleLoggerModule.printErrorString("Error");
	}

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            command line args.
	 * @throws FileNotFoundException
	 */
	public static final void main(final String... args)
			throws FileNotFoundException {
		prepare();
		execute(args);
		unprepare();
	}

	private App() {
	}
}
