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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/29/15 - 19:07 PM
 *
 * @author Jeroen Meulemeester
 */
public class ServiceLocatorTest {

  /**
   * Verify if we just receive 'null' when requesting a non-existing service
   */
  @Test
  public void testGetNonExistentService() {
    assertNull(ServiceLocator.getService("fantastic/unicorn/service"));
    assertNull(ServiceLocator.getService("another/fantastic/unicorn/service"));
  }

  /**
   * Verify if we get the same cached instance when requesting the same service twice
   */
  @Test
  public void testServiceCache() {
    final String[] serviceNames = new String[]{
        "jndi/serviceA", "jndi/serviceB"
    };

    for (final String serviceName : serviceNames) {
      final Service service = ServiceLocator.getService(serviceName);
      assertNotNull(service);
      assertEquals(serviceName, service.getName());
      assertTrue(service.getId() > 0); // The id is generated randomly, but the minimum value is '1'
      assertSame(service, ServiceLocator.getService(serviceName));
    }

  }

}