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
package com.iluwatar.throttling;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class to keep track of the counter of different Tenants
 * @author drastogi
 *
 */
public final class CallsCount {
  private static Map<String, Integer> tenantCallsCount = new ConcurrentHashMap<>();

  /**
   * Add a new tenant to the map.
   * @param tenantName name of the tenant.
   */
  public static void addTenant(String tenantName) {
    if (!tenantCallsCount.containsKey(tenantName)) {
      tenantCallsCount.put(tenantName, 0);
    }
  }
  
  /**
   * Increment the count of the specified tenant.
   * @param tenantName name of the tenant.
   */
  public static void incrementCount(String tenantName) {
    tenantCallsCount.put(tenantName, tenantCallsCount.get(tenantName) + 1);
  }
  
  /**
   * 
   * @param tenantName name of the tenant.
   * @return the count of the tenant.
   */
  public static int getCount(String tenantName) {
    return tenantCallsCount.get(tenantName);
  }
  
  /**
   * Resets the count of all the tenants in the map.
   */
  public static void reset() {
    for (Entry<String, Integer> e : tenantCallsCount.entrySet()) {
      tenantCallsCount.put(e.getKey(), 0);
    }
  }
}
