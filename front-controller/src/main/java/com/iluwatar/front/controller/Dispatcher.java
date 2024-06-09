/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.front.controller;

/**
 * The Dispatcher class is responsible for handling the dispatching of requests to the appropriate
 * command. It retrieves the corresponding command based on the request and invokes the command's
 * process method to handle the business logic.
 */
public class Dispatcher {

  /**
   * Dispatches the request to the appropriate command.
   *
   * @param request the request to be handled
   */
  public void dispatch(String request) {
    var command = getCommand(request);
    command.process();
  }

  /**
   * Retrieves the appropriate command instance for the given request.
   *
   * @param request the request to be handled
   * @return the command instance corresponding to the request
   */
  Command getCommand(String request) {
    var commandClass = getCommandClass(request);
    try {
      return (Command) commandClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new ApplicationException(e);
    }
  }

  /**
   * Retrieves the Class object for the command corresponding to the given request.
   *
   * @param request the request to be handled
   * @return the Class object of the command corresponding to the request
   */
  static Class<?> getCommandClass(String request) {
    try {
      return Class.forName("com.iluwatar.front.controller." + request + "Command");
    } catch (ClassNotFoundException e) {
      return UnknownCommand.class;
    }
  }
}