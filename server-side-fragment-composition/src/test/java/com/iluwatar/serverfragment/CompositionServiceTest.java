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

package com.iluwatar.serverfragment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.serverfragment.composition.CompositionService;
import com.iluwatar.serverfragment.services.ContentService;
import com.iluwatar.serverfragment.services.FooterService;
import com.iluwatar.serverfragment.services.HeaderService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Unit tests for CompositionService demonstrating Server-Side Page Fragment Composition.
 */
class CompositionServiceTest {

  private CompositionService compositionService;
  private HeaderService headerService;
  private ContentService contentService;
  private FooterService footerService;

  @BeforeEach
  void setUp() {
    compositionService = new CompositionService();
    headerService = new HeaderService();
    contentService = new ContentService();
    footerService = new FooterService();
    
    // Register all services
    compositionService.registerService("header", headerService);
    compositionService.registerService("content", contentService);
    compositionService.registerService("footer", footerService);
  }

  @Test
  void testPageComposition() {
    var result = compositionService.composePage("home");
    
    assertNotNull(result);
    assertTrue(result.contains("<header"));
    assertTrue(result.contains("<main"));
    assertTrue(result.contains("<footer"));
    assertTrue(result.contains("DOCTYPE html"));
    assertTrue(result.contains("</html>"));
  }

  @Test
  void testDifferentPageTypes() {
    var homePage = compositionService.composePage("home");
    var aboutPage = compositionService.composePage("about");
    var contactPage = compositionService.composePage("contact");
    
    assertNotEquals(homePage, aboutPage);
    assertNotEquals(aboutPage, contactPage);
    assertNotEquals(homePage, contactPage);
    
    assertTrue(homePage.contains("Welcome Home"));
    assertTrue(aboutPage.contains("About Us"));
    assertTrue(contactPage.contains("Contact Us"));
  }

  @Test
  void testServiceRegistration() {
    var newCompositionService = new CompositionService();
    
    assertDoesNotThrow(() -> newCompositionService.registerService("header", new HeaderService()));
    assertDoesNotThrow(() -> newCompositionService.registerService("content", new ContentService()));
    assertDoesNotThrow(() -> newCompositionService.registerService("footer", new FooterService()));
  }

  @Test
  void testInvalidServiceRegistration() {
    assertThrows(IllegalArgumentException.class, 
        () -> compositionService.registerService("invalid", new Object()));
  }

  @Test
  void testCompositionWithoutRequiredServices() {
    var emptyCompositionService = new CompositionService();
    
    assertThrows(IllegalStateException.class, 
        () -> emptyCompositionService.composePage("home"));
  }

  @Test
  void testCompositionWithPartialServices() {
    var partialCompositionService = new CompositionService();
    partialCompositionService.registerService("header", new HeaderService());
    // Missing content and footer services
    
    assertThrows(IllegalStateException.class, 
        () -> partialCompositionService.composePage("home"));
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testAsynchronousPageComposition() {
    assertDoesNotThrow(() -> {
      var future = compositionService.composePageAsync("home");
      var result = future.get(3, TimeUnit.SECONDS);
      
      assertNotNull(result);
      assertTrue(result.contains("<header"));
      assertTrue(result.contains("<main"));
      assertTrue(result.contains("<footer"));
    });
  }

  @Test
  void testHealthStatus() {
    var healthStatus = compositionService.getHealthStatus();
    
    assertNotNull(healthStatus);
    assertTrue(healthStatus.contains("Header=true"));
    assertTrue(healthStatus.contains("Content=true"));
    assertTrue(healthStatus.contains("Footer=true"));
  }

  @Test
  void testPageContentVariations() {
    var pages = new String[]{"home", "about", "contact", "products", "unknown"};
    
    for (var pageId : pages) {
      var result = compositionService.composePage(pageId);
      
      assertNotNull(result);
      assertTrue(result.contains("DOCTYPE html"));
      assertTrue(result.length() > 1000); // Ensure substantial content
    }
  }

  @Test
  void testCompositionPerformance() {
    var startTime = System.currentTimeMillis();
    
    // Compose multiple pages to test performance
    for (int i = 0; i < 10; i++) {
      compositionService.composePage("home");
    }
    
    var endTime = System.currentTimeMillis();
    var totalTime = endTime - startTime;
    
    // Should complete 10 compositions in reasonable time (less than 5 seconds)
    assertTrue(totalTime < 5000, "Composition should be reasonably fast");
  }
}