/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.monitor;

/**
 * Interface for Monitor Listener which lists all methods required for a listener to monitor object
 */
public interface MonitorListener {

  void nameThisThread(String name);

  void callEnterMonitor(AbstractMonitor monitor);

  void returnFromEnterMonitor(AbstractMonitor monitor);

  void leaveMonitor(AbstractMonitor monitor);

  void callAwait(Condition condition, AbstractMonitor monitor);

  void returnFromAwait(Condition condition, AbstractMonitor monitor);

  void signallerAwakesAwaitingThread(Condition condition, AbstractMonitor monitor);

  void signallerLeavesTemporarily(Condition condition, AbstractMonitor monitor);

  void signallerReenters(Condition condition, AbstractMonitor monitor);

  void signallerLeavesMonitor(Condition condition, AbstractMonitor monitor);

}