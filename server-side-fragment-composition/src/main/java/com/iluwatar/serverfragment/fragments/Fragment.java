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

package com.iluwatar.serverfragment.fragments;

import com.iluwatar.serverfragment.types.PageContext;

/**
 * Base interface for page fragments in Server-Side Page Fragment Composition pattern.
 * 
 * <p>Each fragment represents a portion of a web page that can be independently
 * developed, deployed, and managed by different microservices.
 */
public interface Fragment {
  
  /**
   * Renders the fragment content based on the provided context.
   *
   * @param context The page context containing rendering information
   * @return rendered HTML content as string
   */
  String render(PageContext context);
  
  /**
   * Gets the fragment type identifier.
   *
   * @return fragment type as string
   */
  String getType();
  
  /**
   * Gets the fragment priority for rendering order.
   * Lower values indicate higher priority.
   *
   * @return priority value as integer
   */
  int getPriority();
  
  /**
   * Checks if the fragment is cacheable.
   *
   * @return true if fragment can be cached, false otherwise
   */
  default boolean isCacheable() {
    return true;
  }
  
  /**
   * Gets the cache timeout in seconds.
   *
   * @return cache timeout in seconds
   */
  default int getCacheTimeout() {
    return 300; // 5 minutes default
  }
}