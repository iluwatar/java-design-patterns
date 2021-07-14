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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
class AdapterTest {

  @Test
  void callService() {
    BudgetMicrokernel microkernel = mock(BudgetMicrokernel.class);
    ResultProcessorServer external = mock(ResultProcessorServer.class);
    Mockito.when(microkernel.initCommunication("testRequest")).thenReturn(external);
    Mockito.doNothing().when(external).receiveRequest();
    Adapter adapter = new Adapter(microkernel);

    adapter.callService("testRequest");

    Mockito.verify(microkernel, times(1)).initCommunication("testRequest");
    Mockito.verify(external, times(1)).receiveRequest();
  }
}
