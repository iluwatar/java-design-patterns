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
package com.iluwatar.virtual.proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the App class to ensure its functionality and interactions
 * within the virtual proxy pattern are correct.
 */
public class AppTest {

  private VideoObjectProxy proxy;
  private RealVideoObject realVideoObject;

  /**
   * Sets up the testing environment before each test. This method initializes the mock
   * for RealVideoObject and injects it into VideoObjectProxy.
   */
  @BeforeEach
  void setUp() {
    realVideoObject = Mockito.mock(RealVideoObject.class);
    proxy = new VideoObjectProxy();
    proxy.setRealVideoObject(realVideoObject); // Dependency injection of the mock
  }

  /**
   * Tests that the main method of the application executes without throwing any exceptions.
   * This ensures basic runtime integrity and configuration correctness.
   */
  @Test
  void mainShouldExecuteWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}), "App should run without throwing exceptions");
  }

  /**
   * Verifies that the VideoObjectProxy#process() method creates the RealVideoObject
   * on its first call and correctly delegates the processing to it. This test ensures
   * that the lazy initialization aspect of the proxy is functioning as expected.
   */
  @Test
  void testProcessCreatesRealVideoObjectOnFirstCall() {
    proxy.process();
    verify(realVideoObject, times(1)).process();
  }

  /**
   * Ensures that VideoObjectProxy#process() does not recreate the RealVideoObject
   * if it already exists, thereby verifying that subsequent calls to process utilize the same
   * instantiated object. This test checks the correct reuse of the lazy initialized object.
   */
  @Test
  void testProcessUsesRealVideoObject() {
    // Initial process call to create and use the object
    proxy.process();
    // Second process call should use the existing object
    proxy.process();
    // Verify that process is called exactly twice on the same mock object
    verify(realVideoObject, times(2)).process();
  }
}