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

package com.iluwatar.serverfragment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.serverfragment.types.PageContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for microservices in Server-Side Page Fragment Composition pattern. */
class ServiceTest {

  private PageContext context;

  @BeforeEach
  void setUp() {
    context = PageContext.builder().pageId("test").title("Test Page").userId("testUser").build();
  }

  @Test
  void testHeaderService() {
    var headerService = new HeaderService();

    var fragment = headerService.generateFragment(context);

    assertNotNull(fragment);
    assertTrue(fragment.contains("<header"));
    assertTrue(fragment.contains("Test Page"));
    assertTrue(fragment.contains("Welcome, testUser!"));
    assertTrue(fragment.contains("nav"));

    assertEquals("header", headerService.getFragmentType());
    assertTrue(headerService.isHealthy());
    assertNotNull(headerService.getServiceInfo());
  }

  @Test
  void testHeaderServiceWithAnonymousUser() {
    var anonymousContext =
        PageContext.builder().pageId("test").title("Test Page").build(); // No userId

    var headerService = new HeaderService();
    var fragment = headerService.generateFragment(anonymousContext);

    assertNotNull(fragment);
    assertTrue(fragment.contains("Welcome, Guest!"));
  }

  @Test
  void testContentService() {
    var contentService = new ContentService();

    var fragment = contentService.generateFragment(context);

    assertNotNull(fragment);
    assertTrue(fragment.contains("<main"));
    assertTrue(fragment.contains("Test Page"));

    assertEquals("content", contentService.getFragmentType());
    assertTrue(contentService.isHealthy());
    assertNotNull(contentService.getServiceInfo());
    assertNotNull(contentService.getContentStats());
  }

  @Test
  void testContentServiceDifferentPages() {
    var contentService = new ContentService();

    // Test home page content
    var homeContext = PageContext.builder().pageId("home").title("Home").build();
    var homeFragment = contentService.generateFragment(homeContext);
    assertTrue(homeFragment.contains("Welcome to our Server-Side Fragment"));

    // Test about page content
    var aboutContext = PageContext.builder().pageId("about").title("About").build();
    var aboutFragment = contentService.generateFragment(aboutContext);
    assertTrue(aboutFragment.contains("About Server-Side Page Fragment"));

    // Test contact page content
    var contactContext = PageContext.builder().pageId("contact").title("Contact").build();
    var contactFragment = contentService.generateFragment(contactContext);
    assertTrue(contactFragment.contains("Contact Information"));
  }

  @Test
  void testFooterService() {
    var footerService = new FooterService();

    var fragment = footerService.generateFragment(context);

    assertNotNull(fragment);
    assertTrue(fragment.contains("<footer"));
    assertTrue(fragment.contains("Design Patterns"));
    assertTrue(fragment.contains("All rights reserved"));
    assertTrue(fragment.contains("Footer Service"));

    assertEquals("footer", footerService.getFragmentType());
    assertTrue(footerService.isHealthy());
    assertNotNull(footerService.getServiceInfo());
    assertNotNull(footerService.getPromotionalStatus());
  }

  @Test
  void testPageContextAttributes() {
    context.setAttribute("test.attribute", "test.value");

    assertTrue(context.hasAttribute("test.attribute"));
    assertEquals("test.value", context.getAttribute("test.attribute"));

    context.setAttribute("test.number", 42);
    assertEquals(42, context.getAttribute("test.number"));
  }

  @Test
  void testServiceProcessingTime() {
    var headerService = new HeaderService();
    var contentService = new ContentService();
    var footerService = new FooterService();

    // Measure processing time for each service
    var startTime = System.currentTimeMillis();
    headerService.generateFragment(context);
    var headerTime = System.currentTimeMillis() - startTime;

    startTime = System.currentTimeMillis();
    contentService.generateFragment(context);
    var contentTime = System.currentTimeMillis() - startTime;

    startTime = System.currentTimeMillis();
    footerService.generateFragment(context);
    var footerTime = System.currentTimeMillis() - startTime;

    // All services should complete within reasonable time
    assertTrue(headerTime < 1000, "Header service should complete quickly");
    assertTrue(contentTime < 1000, "Content service should complete quickly");
    assertTrue(footerTime < 1000, "Footer service should complete quickly");
  }
}
