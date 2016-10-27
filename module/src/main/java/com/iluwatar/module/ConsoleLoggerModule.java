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
import java.io.PrintStream;

import org.apache.log4j.Logger;

/**
 * The Module pattern can be considered a Creational pattern and a Structural
 * pattern. It manages the creation and organization of other elements, and
 * groups them as the structural pattern does. An object that applies this
 * pattern can provide the equivalent of a namespace, providing the
 * initialization and finalization process of a static class or a class with
 * static members with cleaner, more concise syntax and semantics.
 * <p>
 * The below example demonstrates a Console logger module, which can print
 * simple and error messages in two designated formats
 */
public class ConsoleLoggerModule {

	private static final Logger logger = Logger
			.getLogger(ConsoleLoggerModule.class);

	private static ConsoleLoggerModule singleton = null;

	public PrintStream output = null;
	public PrintStream error = null;

	private ConsoleLoggerModule() {
	}

	public static final ConsoleLoggerModule getSingleton() {

		if (ConsoleLoggerModule.singleton == null) {
			ConsoleLoggerModule.singleton = new ConsoleLoggerModule();
		}

		return ConsoleLoggerModule.singleton;
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public final void prepare() {

		logger.debug("ConsoleLoggerModule::prepare();");

		this.output = new PrintStream(System.out);
		this.error = new PrintStream(System.err);
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

		logger.debug("ConsoleLoggerModule::unprepare();");
	}

	/**
	 * 
	 * @param value
	 */
	public final void printString(final String value) {
		this.output.println(value);
	}

	/**
	 * 
	 * @param value
	 */
	public final void printErrorString(final String value) {
		this.error.println(value);
	}
}
