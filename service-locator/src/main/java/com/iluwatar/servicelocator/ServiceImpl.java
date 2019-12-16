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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a single service implementation of a sample service. This is the actual service that will
 * process the request. The reference for this service is to be looked upon in the JNDI server that
 * can be set in the web.xml deployment descriptor
 *
 * @author saifasif
 */
public class ServiceImpl implements Service {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImpl.class);

  private final String serviceName;
  private final int id;

  /**
   * Constructor.
   */
  public ServiceImpl(String serviceName) {
    // set the service name
    this.serviceName = serviceName;

    // Generate a random id to this service object
    this.id = (int) Math.floor(Math.random() * 1000) + 1;
  }

  @Override
  public String getName() {
    return serviceName;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void execute() {
    LOGGER.info("Service {} is now executing with id {}", getName(), getId());
  }
}
