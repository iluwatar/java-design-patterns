package com.iluwatar.module;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

/**
 * The MIT License Copyright (c) 2016 Amit Dixit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public final class FilePrinterModule {

	private static final Logger logger = Logger
			.getLogger(FilePrinterModule.class);

	private static FilePrinterModule singleton = null;

	public PrintStream output = null;
	public PrintStream error = null;

	private FilePrinterModule() {
	}

	public static final FilePrinterModule getSingleton() {

		if (FilePrinterModule.singleton == null) {
			FilePrinterModule.singleton = new FilePrinterModule();
		}

		return FilePrinterModule.singleton;
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public final void prepare(final String outputFile, final String errorFile)
			throws FileNotFoundException {

		logger.debug("MainModule::prepare();");

		this.output = new PrintStream(new FileOutputStream(outputFile));
		this.error = new PrintStream(new FileOutputStream(errorFile));
	}

	/**
	 * 
	 */
	public final void unprepare() {

		if (this.output != null) {

			this.output.flush();
			this.output.close();
		}

		if (this.error != null) {

			this.error.flush();
			this.error.close();
		}

		logger.debug("MainModule::unprepare();");
	}

	/**
	 * 
	 * @param value
	 */
	public final void printString(final String value) {
		this.output.print(value);
	}

	/**
	 * 
	 * @param value
	 */
	public final void printErrorString(final String value) {
		this.error.print(value);
	}
}
