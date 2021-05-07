/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.poison.pill;

import java.util.Map;

/**
 * Interface that implements the Message pattern and represents an inbound or outbound message as
 * part of an {@link Producer}-{@link Consumer} exchange.
 */
public interface Message {

  Message POISON_PILL = new Message() {

    @Override
    public void addHeader(Headers header, String value) {
      throw poison();
    }

    @Override
    public String getHeader(Headers header) {
      throw poison();
    }

    @Override
    public Map<Headers, String> getHeaders() {
      throw poison();
    }

    @Override
    public void setBody(String body) {
      throw poison();
    }

    @Override
    public String getBody() {
      throw poison();
    }

    private RuntimeException poison() {
      return new UnsupportedOperationException("Poison");
    }

  };

  /**
   * Enumeration of Type of Headers.
   */
  enum Headers {
    DATE, SENDER
  }

  void addHeader(Headers header, String value);

  String getHeader(Headers header);

  Map<Headers, String> getHeaders();

  void setBody(String body);

  String getBody();
}
