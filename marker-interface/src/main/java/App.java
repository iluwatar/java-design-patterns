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

import org.slf4j.LoggerFactory;

/**
 * Created by Alexis on 28-Apr-17. With Marker interface idea is to make empty interface and extend
 * it. Basically it is just to identify the special objects from normal objects. Like in case of
 * serialization , objects that need to be serialized must implement serializable interface (it is
 * empty interface) and down the line writeObject() method must be checking if it is an instance of
 * serializable or not.
 *
 * <p>Marker interface vs annotation Marker interfaces and marker annotations both have their uses,
 * neither of them is obsolete or always better than the other one. If you want to define a type
 * that does not have any new methods associated with it, a marker interface is the way to go. If
 * you want to mark program elements other than classes and interfaces, to allow for the possibility
 * of adding more information to the marker in the future, or to fit the marker into a framework
 * that already makes heavy use of annotation types, then a marker annotation is the correct choice
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    final var logger = LoggerFactory.getLogger(App.class);
    var guard = new Guard();
    var thief = new Thief();

    //noinspection ConstantConditions
    if (guard instanceof Permission) {
      guard.enter();
    } else {
      logger.info("You have no permission to enter, please leave this area");
    }

    //noinspection ConstantConditions
    if (thief instanceof Permission) {
      thief.steal();
    } else {
      thief.doNothing();
    }
  }
}

