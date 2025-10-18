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

package com.iluwatar.serverfragment.composition;

import com.iluwatar.serverfragment.services.ContentService;
import com.iluwatar.serverfragment.services.FooterService;
import com.iluwatar.serverfragment.services.HeaderService;
import com.iluwatar.serverfragment.types.PageContext;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Main composition service that orchestrates fragment assembly from microservices.
 *
 * <p>This service acts as the central coordinator for the Server-Side Page Fragment Composition
 * pattern. It manages microservice registration, handles fragment requests, and coordinates the
 * final page assembly process.
 */
@Slf4j
public class CompositionService {

  private final Map<String, HeaderService> headerServices = new HashMap<>();
  private final Map<String, ContentService> contentServices = new HashMap<>();
  private final Map<String, FooterService> footerServices = new HashMap<>();
  private final PageComposer pageComposer = new PageComposer();

  /**
   * Registers a microservice for fragment composition.
   *
   * <p>This method allows different microservices to be registered with the composition service. In
   * a real distributed system, this would involve service discovery and registration mechanisms.
   *
   * @param type the type of service (header, content, footer)
   * @param service the service instance to register
   * @throws IllegalArgumentException if service type is unknown
   */
  public void registerService(String type, Object service) {
    switch (type.toLowerCase()) {
      case "header" -> {
        headerServices.put(type, (HeaderService) service);
        LOGGER.info(
            "CompositionService: Registered HeaderService - {}",
            ((HeaderService) service).getServiceInfo());
      }
      case "content" -> {
        contentServices.put(type, (ContentService) service);
        LOGGER.info(
            "CompositionService: Registered ContentService - {}",
            ((ContentService) service).getServiceInfo());
      }
      case "footer" -> {
        footerServices.put(type, (FooterService) service);
        LOGGER.info(
            "CompositionService: Registered FooterService - {}",
            ((FooterService) service).getServiceInfo());
      }
      default -> {
        var errorMessage = "Unknown service type: " + type;
        LOGGER.warn("CompositionService: {}", errorMessage);
        throw new IllegalArgumentException(errorMessage);
      }
    }
  }

  /**
   * Composes a complete page from registered microservices.
   *
   * <p>This method orchestrates the entire page composition process, including context creation,
   * fragment generation, and final assembly.
   *
   * @param pageId the identifier for the page to compose
   * @return complete HTML page as a string
   * @throws IllegalStateException if required services are not registered
   */
  public String composePage(String pageId) {
    LOGGER.info("CompositionService: Starting page composition for pageId: {}", pageId);

    var startTime = System.currentTimeMillis();

    // Validate that required services are registered
    validateRequiredServices();

    // Create page context
    var context = createPageContext(pageId);

    // Generate fragments from microservices (sequentially for this demo)
    var headerContent = generateHeaderFragment(context);
    var mainContent = generateContentFragment(context);
    var footerContent = generateFooterFragment(context);

    // Compose final page
    var completePage = pageComposer.composePage(headerContent, mainContent, footerContent);

    var totalTime = System.currentTimeMillis() - startTime;
    LOGGER.info(
        "CompositionService: Page composition completed in {}ms for pageId: {}", totalTime, pageId);

    return completePage;
  }

  /**
   * Composes a page asynchronously using parallel fragment generation.
   *
   * <p>This method demonstrates how fragment generation can be parallelized to improve performance
   * in a real microservice environment.
   *
   * @param pageId the identifier for the page to compose
   * @return CompletableFuture with the complete HTML page
   */
  public CompletableFuture<String> composePageAsync(String pageId) {
    LOGGER.info("CompositionService: Starting async page composition for pageId: {}", pageId);

    validateRequiredServices();
    var context = createPageContext(pageId);

    // Generate fragments in parallel
    var headerFuture = CompletableFuture.supplyAsync(() -> generateHeaderFragment(context));
    var contentFuture = CompletableFuture.supplyAsync(() -> generateContentFragment(context));
    var footerFuture = CompletableFuture.supplyAsync(() -> generateFooterFragment(context));

    // Combine results and compose page
    return CompletableFuture.allOf(headerFuture, contentFuture, footerFuture)
        .thenApply(
            v -> {
              try {
                var headerContent = headerFuture.get(5, TimeUnit.SECONDS);
                var mainContent = contentFuture.get(5, TimeUnit.SECONDS);
                var footerContent = footerFuture.get(5, TimeUnit.SECONDS);

                return pageComposer.composePage(headerContent, mainContent, footerContent);
              } catch (Exception e) {
                LOGGER.error("CompositionService: Error in async page composition", e);
                throw new RuntimeException("Page composition failed", e);
              }
            });
  }

  /**
   * Validates that all required services are registered.
   *
   * @throws IllegalStateException if any required service is missing
   */
  private void validateRequiredServices() {
    if (headerServices.isEmpty()) {
      throw new IllegalStateException("No header service registered");
    }
    if (contentServices.isEmpty()) {
      throw new IllegalStateException("No content service registered");
    }
    if (footerServices.isEmpty()) {
      throw new IllegalStateException("No footer service registered");
    }
  }

  /**
   * Creates a page context for the given page ID.
   *
   * @param pageId the page identifier
   * @return configured PageContext
   */
  private PageContext createPageContext(String pageId) {
    return PageContext.builder()
        .pageId(pageId)
        .title(getPageTitle(pageId))
        .userId(getCurrentUserId()) // In real system, this would come from session/auth
        .build();
  }

  /**
   * Gets the title for a specific page.
   *
   * @param pageId the page identifier
   * @return page title
   */
  private String getPageTitle(String pageId) {
    return switch (pageId.toLowerCase()) {
      case "home" -> "Welcome Home - Fragment Composition Demo";
      case "about" -> "About Us - Fragment Composition Demo";
      case "contact" -> "Contact Us - Fragment Composition Demo";
      case "products" -> "Our Products - Fragment Composition Demo";
      default -> "Page - Fragment Composition Demo";
    };
  }

  /**
   * Gets the current user ID (simulated for demo purposes).
   *
   * @return user ID or null if not authenticated
   */
  private String getCurrentUserId() {
    // Simulate authenticated user 50% of the time
    return System.currentTimeMillis() % 2 == 0 ? "user123" : null;
  }

  /** Generates header fragment from the header service. */
  private String generateHeaderFragment(PageContext context) {
    return headerServices.get("header").generateFragment(context);
  }

  /** Generates content fragment from the content service. */
  private String generateContentFragment(PageContext context) {
    return contentServices.get("content").generateFragment(context);
  }

  /** Generates footer fragment from the footer service. */
  private String generateFooterFragment(PageContext context) {
    return footerServices.get("footer").generateFragment(context);
  }

  /**
   * Gets composition service health status.
   *
   * @return health status summary
   */
  public String getHealthStatus() {
    var headerHealthy = headerServices.values().stream().allMatch(HeaderService::isHealthy);
    var contentHealthy = contentServices.values().stream().allMatch(ContentService::isHealthy);
    var footerHealthy = footerServices.values().stream().allMatch(FooterService::isHealthy);

    return String.format(
        "CompositionService Health: Header=%s, Content=%s, Footer=%s",
        headerHealthy, contentHealthy, footerHealthy);
  }
}
