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

package com.iluwatar.servicelocator;

/**
 * The service locator module. Will fetch service from cache, otherwise creates a fresh service and
 * update cache
 *
 * @author saifasif
 */
public final class ServiceLocator {

  private static ServiceCache serviceCache = new ServiceCache();

  private ServiceLocator() {
  }

  /**
   * Fetch the service with the name param from the cache first, if no service is found, lookup the
   * service from the {@link InitContext} and then add the newly created service into the cache map
   * for future requests.
   *
   * @param serviceJndiName a string
   * @return {@link Service}
   */
  public static Service getService(String serviceJndiName) {
    var serviceObj = serviceCache.getService(serviceJndiName);
    if (serviceObj != null) {
      return serviceObj;
    } else {
      /*
       * If we are unable to retrive anything from cache, then lookup the service and add it in the
       * cache map
       */
      var ctx = new InitContext();
      serviceObj = (Service) ctx.lookup(serviceJndiName);
      if (serviceObj != null) { // Only cache a service if it actually exists
        serviceCache.addService(serviceObj);
      }
      return serviceObj;
    }
  }
}
