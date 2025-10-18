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

import com.iluwatar.serverfragment.fragments.Fragment;
import com.iluwatar.serverfragment.fragments.HeaderFragment;
import com.iluwatar.serverfragment.types.PageContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Header microservice responsible for managing header fragments.
 *
 * <p>This service represents an independent microservice that can be developed, deployed, and
 * scaled separately from other services. It encapsulates all header-related logic and rendering.
 */
@Slf4j
public class HeaderService {

  private final Fragment headerFragment = new HeaderFragment();

  /**
   * Generates header fragment for the given context.
   *
   * <p>This method simulates a microservice endpoint that would be called over HTTP in a real
   * distributed system.
   *
   * @param context page context containing rendering information
   * @return rendered header fragment as HTML string
   */
  public String generateFragment(PageContext context) {
    LOGGER.info(
        "HeaderService: Processing request for page {} (User: {})",
        context.getPageId(),
        context.getUserId() != null ? context.getUserId() : "anonymous");

    // Simulate some processing time that might occur in a real microservice
    simulateProcessing();

    // Add service-specific context attributes
    context.setAttribute("headerService.version", "1.2.0");
    context.setAttribute("headerService.timestamp", System.currentTimeMillis());

    var renderedFragment = headerFragment.render(context);

    LOGGER.debug(
        "HeaderService: Generated fragment of length {} characters", renderedFragment.length());

    return renderedFragment;
  }

  /**
   * Simulates processing time that would occur in a real microservice. This might include database
   * queries, external API calls, etc.
   */
  private void simulateProcessing() {
    try {
      Thread.sleep(50); // Simulate 50ms processing time
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.warn("HeaderService processing was interrupted", e);
    }
  }

  /**
   * Gets service metadata information.
   *
   * @return service name and version
   */
  public String getServiceInfo() {
    return "Header Service v1.2.0 - Manages website header and navigation";
  }

  /**
   * Health check endpoint for service monitoring.
   *
   * @return true if service is healthy
   */
  public boolean isHealthy() {
    return true; // In real implementation, this would check dependencies
  }

  /**
   * Gets the fragment type this service handles.
   *
   * @return fragment type identifier
   */
  public String getFragmentType() {
    return headerFragment.getType();
  }
}
