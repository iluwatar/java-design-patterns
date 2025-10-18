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

package com.iluwatar.serverfragment.types;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Context object containing page-specific information for fragment rendering.
 * 
 * <p>This class encapsulates all the data that fragments need to render
 * themselves appropriately for the specific page and user context.
 */
@Data
@Builder
public class PageContext {
  
  /**
   * Unique identifier for the page being rendered.
   */
  private String pageId;
  
  /**
   * Title of the page to be displayed.
   */
  private String title;
  
  /**
   * User identifier for personalization.
   */
  private String userId;
  
  /**
   * Additional attributes that can be used by fragments.
   */
  @Builder.Default
  private Map<String, Object> attributes = new HashMap<>();
  
  /**
   * Gets an attribute value by key.
   *
   * @param key the attribute key
   * @return the attribute value, or null if not found
   */
  public Object getAttribute(String key) {
    return attributes.get(key);
  }
  
  /**
   * Sets an attribute value.
   *
   * @param key the attribute key
   * @param value the attribute value
   */
  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }
  
  /**
   * Checks if an attribute exists.
   *
   * @param key the attribute key
   * @return true if the attribute exists, false otherwise
   */
  public boolean hasAttribute(String key) {
    return attributes.containsKey(key);
  }
}