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

import com.iluwatar.serverfragment.fragments.FooterFragment;
import com.iluwatar.serverfragment.fragments.Fragment;
import com.iluwatar.serverfragment.types.PageContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Footer microservice responsible for managing footer fragments.
 *
 * <p>This service represents an independent microservice that handles footer content, including
 * dynamic elements like current time, links, and promotional content that might change frequently.
 */
@Slf4j
public class FooterService {

  private final Fragment footerFragment = new FooterFragment();

  /**
   * Generates footer fragment for the given context.
   *
   * <p>This method simulates a microservice endpoint that might include dynamic footer content,
   * promotional banners, social media feeds, or real-time information.
   *
   * @param context page context containing rendering information
   * @return rendered footer fragment as HTML string
   */
  public String generateFragment(PageContext context) {
    LOGGER.info(
        "FooterService: Processing footer request for page {} (User: {})",
        context.getPageId(),
        context.getUserId() != null ? context.getUserId() : "anonymous");

    // Simulate footer-specific processing
    simulateFooterProcessing();

    // Add footer-specific metadata
    context.setAttribute("footerService.version", "1.3.0");
    context.setAttribute("footerService.timestamp", System.currentTimeMillis());
    context.setAttribute("footerService.dynamicContent", true);

    // Add promotional content if applicable
    addPromotionalContent(context);

    var renderedFragment = footerFragment.render(context);

    LOGGER.debug(
        "FooterService: Generated footer fragment of length {} characters",
        renderedFragment.length());

    return renderedFragment;
  }

  /**
   * Adds promotional or dynamic content to the footer context.
   *
   * <p>This simulates how a footer service might inject promotional banners, announcements, or
   * other dynamic content.
   *
   * @param context page context to enhance with promotional content
   */
  private void addPromotionalContent(PageContext context) {
    // Simulate checking for active promotions
    var hasActivePromotion = System.currentTimeMillis() % 2 == 0; // Random for demo

    if (hasActivePromotion) {
      context.setAttribute(
          "footer.promotion", "🎉 Special offer: 20% off all design pattern books!");
      context.setAttribute("footer.promotionLink", "/promotions/books");
      LOGGER.debug("FooterService: Added promotional content to footer");
    }

    // Add social media feed timestamp (simulated)
    context.setAttribute("footer.socialFeedUpdate", System.currentTimeMillis());
  }

  /**
   * Simulates footer-specific processing. This might include retrieving social media feeds,
   * checking for announcements, or updating promotional content.
   */
  private void simulateFooterProcessing() {
    try {
      Thread.sleep(30); // Simulate 30ms processing time
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.warn("FooterService processing was interrupted", e);
    }
  }

  /**
   * Gets service metadata information.
   *
   * @return service name and version
   */
  public String getServiceInfo() {
    return "Footer Service v1.3.0 - Manages website footer and dynamic promotional content";
  }

  /**
   * Health check endpoint for service monitoring.
   *
   * @return true if service is healthy
   */
  public boolean isHealthy() {
    return true; // In real implementation, this would check external dependencies
  }

  /**
   * Gets the fragment type this service handles.
   *
   * @return fragment type identifier
   */
  public String getFragmentType() {
    return footerFragment.getType();
  }

  /**
   * Gets current promotional status for monitoring.
   *
   * @return promotional content status
   */
  public String getPromotionalStatus() {
    return "Active promotions: 2, Social feed updates: enabled, Last update: "
        + System.currentTimeMillis();
  }
}
