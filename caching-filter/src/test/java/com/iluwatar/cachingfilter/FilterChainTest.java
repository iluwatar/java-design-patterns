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
package com.iluwatar.cachingfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FilterChainTest {

  @Test
  void runsFiltersInOrderBeforeHandler() {
    var calls = new ArrayList<String>();
    Filter first =
        (request, chain) -> {
          calls.add("first");
          return chain.proceed(request);
        };
    Filter second =
        (request, chain) -> {
          calls.add("second");
          return chain.proceed(request);
        };
    Handler handler =
        request -> {
          calls.add("handler");
          return new Response(Response.OK, request.path());
        };

    var response = new FilterChain(List.of(first, second), handler).proceed(Request.get("/a"));

    assertEquals(List.of("first", "second", "handler"), calls);
    assertEquals(new Response(Response.OK, "/a"), response);
  }

  @Test
  void filterCanAnswerWithoutCallingHandler() {
    Filter shortCircuit = (request, chain) -> new Response(Response.NOT_FOUND, "blocked");
    Handler handler =
        request -> {
          throw new AssertionError("handler must not run");
        };

    var response = new FilterChain(List.of(shortCircuit), handler).proceed(Request.get("/a"));

    assertEquals("blocked", response.body());
  }

  @Test
  void emptyChainCallsHandlerDirectly() {
    var response =
        new FilterChain(List.of(), request -> new Response(Response.OK, "done"))
            .proceed(Request.get("/a"));

    assertEquals("done", response.body());
  }
}
