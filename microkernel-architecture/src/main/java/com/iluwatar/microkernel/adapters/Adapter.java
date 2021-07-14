/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.microkernel.adapters;

import com.iluwatar.microkernel.externals.ResultProcessorServer;
import com.iluwatar.microkernel.microkernel.BudgetMicrokernel;

/**
 * This class represents the adapter.
 * This class hides system dependencies such as
 * communication facilities from the client,
 * invokes methods of external servers on behalf of clients.
 */
public class Adapter {

  private BudgetMicrokernel microkernel;

  /**
   * Use this constructor to create an Adapter with all details.
   * @param microkernel as the core which does the basic calculations
   */
  public Adapter(BudgetMicrokernel microkernel) {
    this.microkernel = microkernel;
  }

  /**
   * Receives the request and calls method for executing.
   * @param request as request to be executed
   */
  public void callService(String request) {
    createRequest(request);
  }

  /**
   * Constructs a request and asks the microkernel for a
   * communication link with the external server,
   * and sends the request to it with the request.
   * @param request as request to be executed
   */
  private void createRequest(String request) {
    ResultProcessorServer externalServer = this.microkernel.initCommunication(request);
    if (externalServer != null) {
      externalServer.receiveRequest();
    }
  }
}
