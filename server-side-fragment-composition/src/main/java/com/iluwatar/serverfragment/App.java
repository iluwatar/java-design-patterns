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

import com.iluwatar.serverfragment.composition.CompositionService;
import com.iluwatar.serverfragment.services.ContentService;
import com.iluwatar.serverfragment.services.FooterService;
import com.iluwatar.serverfragment.services.HeaderService;
import lombok.extern.slf4j.Slf4j;

/**
 * Server-Side Page Fragment Composition pattern demonstration.
 * 
 * <p>This pattern demonstrates how to compose web pages from fragments 
 * delivered by different microservices. Each fragment is managed independently,
 * allowing for modular development, deployment, and scaling.
 * 
 * <p>The key elements of this pattern include:
 * <ul>
 *   <li><strong>Fragment Composition:</strong> Server assembles webpage from various fragments</li>
 *   <li><strong>Microservices:</strong> Each fragment managed by separate microservice</li>
 *   <li><strong>Scalability:</strong> Independent development, deployment, and scaling</li>
 *   <li><strong>Performance:</strong> Server-side assembly optimizes client performance</li>
 *   <li><strong>Consistent UX:</strong> Ensures cohesive user experience across fragments</li>
 * </ul>
 * 
 * <p>In this demo, we simulate three microservices:
 * <ul>
 *   <li><strong>HeaderService:</strong> Manages navigation and branding</li>
 *   <li><strong>ContentService:</strong> Manages main page content and personalization</li>
 *   <li><strong>FooterService:</strong> Manages footer content and promotional information</li>
 * </ul>
 */
@Slf4j
public class App {

  /**
   * Main method demonstrating Server-Side Page Fragment Composition.
   * 
   * <p>This demonstration shows how multiple microservices can be coordinated
   * to generate complete web pages, emphasizing the independence and scalability
   * of each service while maintaining a cohesive user experience.
   * 
   * @param args command line arguments (not used in this demo)
   */
  public static void main(String[] args) {
    LOGGER.info("=== Starting Server-Side Page Fragment Composition Demo ===");
    
    try {
      // Initialize and demonstrate the pattern
      var demo = new App();
      demo.runDemo();
      
      LOGGER.info("=== Server-Side Page Fragment Composition Demo Completed Successfully ===");
      
    } catch (Exception e) {
      LOGGER.error("Demo execution failed", e);
      System.exit(1);
    }
  }

  /**
   * Runs the complete demonstration of the Server-Side Page Fragment Composition pattern.
   */
  private void runDemo() {
    LOGGER.info("Initializing microservices...");
    
    // Create microservice instances
    // In a real system, these would be separate deployable services
    var headerService = new HeaderService();
    var contentService = new ContentService();
    var footerService = new FooterService();
    
    LOGGER.info("Microservices initialized:");
    LOGGER.info("  - {}", headerService.getServiceInfo());
    LOGGER.info("  - {}", contentService.getServiceInfo());
    LOGGER.info("  - {}", footerService.getServiceInfo());
    
    // Create and configure composition service
    LOGGER.info("Setting up composition service...");
    var compositionService = new CompositionService();
    
    // Register microservices with the composition service
    compositionService.registerService("header", headerService);
    compositionService.registerService("content", contentService);
    compositionService.registerService("footer", footerService);
    
    // Check health status
    LOGGER.info("Health Status: {}", compositionService.getHealthStatus());
    
    // Demonstrate synchronous page composition for different page types
    demonstrateSynchronousComposition(compositionService);
    
    // Demonstrate asynchronous page composition
    demonstrateAsynchronousComposition(compositionService);
    
    // Demonstrate error handling
    demonstrateErrorHandling();
  }

  /**
   * Demonstrates synchronous page composition for various page types.
   */
  private void demonstrateSynchronousComposition(CompositionService compositionService) {
    LOGGER.info("\n--- Demonstrating Synchronous Page Composition ---");
    
    var pageTypes = new String[]{"home", "about", "contact", "products"};
    
    for (var pageType : pageTypes) {
      LOGGER.info("Composing '{}' page...", pageType);
      
      var startTime = System.currentTimeMillis();
      var completePage = compositionService.composePage(pageType);
      var compositionTime = System.currentTimeMillis() - startTime;
      
      LOGGER.info("'{}' page composed in {}ms (size: {} characters)", 
          pageType, compositionTime, completePage.length());
      
      // Log a sample of the composed page for verification
      var preview = getPagePreview(completePage);
      LOGGER.debug("Page preview for '{}': {}", pageType, preview);
    }
  }

  /**
   * Demonstrates asynchronous page composition for improved performance.
   */
  private void demonstrateAsynchronousComposition(CompositionService compositionService) {
    LOGGER.info("\n--- Demonstrating Asynchronous Page Composition ---");
    
    var pageType = "home";
    LOGGER.info("Composing '{}' page asynchronously...", pageType);
    
    var startTime = System.currentTimeMillis();
    
    try {
      var futureResult = compositionService.composePageAsync(pageType);
      var completePage = futureResult.get(); // Wait for completion
      var compositionTime = System.currentTimeMillis() - startTime;
      
      LOGGER.info("Async '{}' page composed in {}ms (size: {} characters)", 
          pageType, compositionTime, completePage.length());
      
      var preview = getPagePreview(completePage);
      LOGGER.debug("Async page preview: {}", preview);
      
    } catch (Exception e) {
      LOGGER.error("Async composition failed for page: {}", pageType, e);
    }
  }

  /**
   * Demonstrates error handling in the composition process.
   */
  private void demonstrateErrorHandling() {
    LOGGER.info("\n--- Demonstrating Error Handling ---");
    
    // Create a new composition service without registered services
    var emptyCompositionService = new CompositionService();
    
    try {
      LOGGER.info("Attempting to compose page without registered services...");
      emptyCompositionService.composePage("test");
      
    } catch (IllegalStateException e) {
      LOGGER.info("Expected error caught: {}", e.getMessage());
      LOGGER.info("Error handling working correctly - services must be registered");
    }
    
    // Demonstrate invalid service registration
    var validCompositionService = new CompositionService();
    try {
      LOGGER.info("Attempting to register invalid service type...");
      validCompositionService.registerService("invalid", new Object());
      
    } catch (IllegalArgumentException e) {
      LOGGER.info("Expected error caught: {}", e.getMessage());
      LOGGER.info("Error handling working correctly - only valid service types accepted");
    }
  }

  /**
   * Extracts a preview of the composed page for logging purposes.
   *
   * @param completePage the complete HTML page
   * @return a short preview of the page content
   */
  private String getPagePreview(String completePage) {
    if (completePage == null || completePage.length() < 100) {
      return completePage;
    }
    
    // Extract title and first bit of content for preview
    var titleStart = completePage.indexOf("<title>");
    var titleEnd = completePage.indexOf("</title>");
    
    if (titleStart != -1 && titleEnd != -1) {
      var title = completePage.substring(titleStart + 7, titleEnd);
      return String.format("Title: '%s', Length: %d chars", title, completePage.length());
    }
    
    return String.format("HTML page, Length: %d chars", completePage.length());
  }
}