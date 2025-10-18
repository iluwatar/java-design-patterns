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

import com.iluwatar.serverfragment.fragments.ContentFragment;
import com.iluwatar.serverfragment.fragments.Fragment;
import com.iluwatar.serverfragment.types.PageContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Content microservice responsible for managing main content fragments.
 * 
 * <p>This service represents an independent microservice that handles
 * the main content area of web pages. It can manage complex business logic,
 * data retrieval, and content personalization.
 */
@Slf4j
public class ContentService {

  private final Fragment contentFragment = new ContentFragment();

  /**
   * Generates content fragment for the given context.
   * 
   * <p>This method simulates a microservice endpoint that would handle
   * complex content generation, possibly including database queries,
   * content management system integration, and personalization logic.
   *
   * @param context page context containing rendering information
   * @return rendered content fragment as HTML string
   */
  public String generateFragment(PageContext context) {
    LOGGER.info("ContentService: Processing content request for page {} (User: {})", 
        context.getPageId(), 
        context.getUserId() != null ? context.getUserId() : "anonymous");
    
    // Simulate more complex processing for content generation
    simulateContentProcessing();
    
    // Add content-specific metadata
    context.setAttribute("contentService.version", "2.1.0");
    context.setAttribute("contentService.generatedAt", System.currentTimeMillis());
    context.setAttribute("contentService.personalized", context.getUserId() != null);
    
    // Apply personalization if user is identified
    if (context.getUserId() != null) {
      applyPersonalization(context);
    }
    
    var renderedFragment = contentFragment.render(context);
    
    LOGGER.debug("ContentService: Generated content fragment of length {} characters", 
        renderedFragment.length());
    
    return renderedFragment;
  }

  /**
   * Applies personalization to the content based on user context.
   *
   * @param context page context to personalize
   */
  private void applyPersonalization(PageContext context) {
    LOGGER.debug("ContentService: Applying personalization for user {}", context.getUserId());
    
    // Simulate personalization logic
    context.setAttribute("personalization.applied", true);
    context.setAttribute("personalization.userId", context.getUserId());
    
    // In a real system, this might involve:
    // - Retrieving user preferences
    // - Customizing content based on user history
    // - A/B testing different content variants
    // - Localization based on user location
  }

  /**
   * Simulates complex content processing that might occur in a real microservice.
   * This could include database queries, external API calls, content transformation, etc.
   */
  private void simulateContentProcessing() {
    try {
      Thread.sleep(100); // Simulate 100ms processing time for content generation
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.warn("ContentService processing was interrupted", e);
    }
  }

  /**
   * Gets service metadata information.
   *
   * @return service name and version
   */
  public String getServiceInfo() {
    return "Content Service v2.1.0 - Manages dynamic page content and personalization";
  }
  
  /**
   * Health check endpoint for service monitoring.
   *
   * @return true if service is healthy
   */
  public boolean isHealthy() {
    return true; // In real implementation, this would check database connections, etc.
  }
  
  /**
   * Gets the fragment type this service handles.
   *
   * @return fragment type identifier
   */
  public String getFragmentType() {
    return contentFragment.getType();
  }
  
  /**
   * Gets content statistics for monitoring purposes.
   *
   * @return content generation statistics
   */
  public String getContentStats() {
    return "Average content generation time: 100ms, Cache hit rate: 15%";
  }
}