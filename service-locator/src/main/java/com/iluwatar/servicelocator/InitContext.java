/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For JNDI lookup of services from the web.xml. Will match name of the service name that is being
 * requested and return a newly created service object with the name
 *
 * @author saifasif
 */
public class InitContext {

  private static final Logger LOGGER = LoggerFactory.getLogger(InitContext.class);

  /**
   * Perform the lookup based on the service name. The returned object will need to be casted into a
   * {@link Service}
   *
   * @param serviceName a string
   * @return an {@link Object}
   */
  public Object lookup(String serviceName) {
    if (serviceName.equals("jndi/serviceA")) {
      LOGGER.info("Looking up service A and creating new service for A");
      return new ServiceImpl("jndi/serviceA");
    } else if (serviceName.equals("jndi/serviceB")) {
      LOGGER.info("Looking up service B and creating new service for B");
      return new ServiceImpl("jndi/serviceB");
    } else {
      return null;
    }
  }
}
