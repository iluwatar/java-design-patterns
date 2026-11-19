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

import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * The slow back end behind the filter chain. It looks up product descriptions by path and counts
 * how often it is called, which shows how many requests the cache saves.
 */
@Slf4j
public class ProductCatalog implements Handler {

  private final Map<String, String> products;

  /** Number of requests that reached the catalog. */
  @Getter private int lookups;

  /** Create a catalog serving the given products, keyed by path. */
  public ProductCatalog(Map<String, String> products) {
    this.products = Map.copyOf(products);
  }

  @Override
  public Response handle(Request request) {
    lookups++;
    LOGGER.info("Catalog lookup #{} for {}", lookups, request.path());
    var product = products.get(request.path());
    return product == null
        ? new Response(Response.NOT_FOUND, "No product at " + request.path())
        : new Response(Response.OK, product);
  }
}
