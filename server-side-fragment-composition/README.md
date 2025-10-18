---
title: Server-Side Page Fragment Composition
category: Architectural
language: en
tag:
 - Microservices
 - Web development
 - Scalability
 - Performance
 - Composition
---

## Intent

Compose web pages from various fragments that are managed by different microservices, enabling modular development, independent deployment, and enhanced scalability.

## Explanation

Real-world example

> Consider a modern e-commerce website where different teams manage different parts of the page. The header team manages navigation and branding, the product team manages the main content area, and the marketing team manages the footer with promotions. Using Server-Side Page Fragment Composition, each team can develop, deploy, and scale their components independently while the composition service assembles them into cohesive pages.

In plain words

> Server-Side Page Fragment Composition allows you to build web pages by combining fragments from different microservices on the server side, promoting modularity and independent scaling.

Wikipedia says

> Server-side composition involves assembling a complete webpage from fragments rendered by different services on the server before sending the result to the client. This approach optimizes performance and enables independent development of page components.

## Programmatic Example

In our Server-Side Page Fragment Composition implementation, we have different microservices responsible for different page fragments:

```java
// Create microservices
var headerService = new HeaderService();
var contentService = new ContentService();  
var footerService = new FooterService();

// Create composition service
var compositionService = new CompositionService();
compositionService.registerService("header", headerService);
compositionService.registerService("content", contentService);
compositionService.registerService("footer", footerService);

// Compose complete page
var completePage = compositionService.composePage("home");
```

Each fragment service manages its own rendering logic:

```java
public class HeaderService {
  public String generateFragment(PageContext context) {
    LOGGER.info("HeaderService: Processing request for page {}", context.getPageId());
    return headerFragment.render(context);
  }
}
```

The fragments implement a common interface:

```java
public interface Fragment {
  String render(PageContext context);
  String getType();
  int getPriority();
  default boolean isCacheable() { return true; }
  default int getCacheTimeout() { return 300; }
}
```

Each fragment renders its specific portion of the page:

```java
public class HeaderFragment implements Fragment {
  @Override
  public String render(PageContext context) {
    var userInfo = context.getUserId() != null 
        ? String.format("Welcome, %s!", context.getUserId()) 
        : "Welcome, Guest!";
    
    return String.format("""
        <header class="site-header">
          <h1>%s</h1>
          <nav><!-- navigation links --></nav>
          <div class="user-info">%s</div>
        </header>
        """, context.getTitle(), userInfo);
  }
}
```

The composition service orchestrates the assembly:

```java
public class CompositionService {
  public String composePage(String pageId) {
    var context = createPageContext(pageId);
    
    // Fetch fragments from microservices
    var headerContent = headerServices.get("header").generateFragment(context);
    var mainContent = contentServices.get("content").generateFragment(context);
    var footerContent = footerServices.get("footer").generateFragment(context);
    
    return pageComposer.composePage(headerContent, mainContent, footerContent);
  }
}
```

The page composer assembles the final HTML:

```java
public class PageComposer {
  public String composePage(String headerContent, String mainContent, String footerContent) {
    return String.format("""
        <!DOCTYPE html>
        <html>
        <head><!-- head content --></head>
        <body>
          %s  <!-- Header -->
          %s  <!-- Main Content -->
          %s  <!-- Footer -->
        </body>
        </html>
        """, headerContent, mainContent, footerContent);
  }
}
```

The pattern also supports asynchronous composition for better performance:

```java
public CompletableFuture<String> composePageAsync(String pageId) {
  var context = createPageContext(pageId);
  
  // Generate fragments in parallel
  var headerFuture = CompletableFuture.supplyAsync(() -> generateHeaderFragment(context));
  var contentFuture = CompletableFuture.supplyAsync(() -> generateContentFragment(context));
  var footerFuture = CompletableFuture.supplyAsync(() -> generateFooterFragment(context));
  
  return CompletableFuture.allOf(headerFuture, contentFuture, footerFuture)
      .thenApply(v -> pageComposer.composePage(
          headerFuture.join(), contentFuture.join(), footerFuture.join()));
}
```

## Class diagram

![Server-Side Fragment Composition](./etc/server-side-fragment-composition.urm.png)

## Applicability

Use Server-Side Page Fragment Composition when:

* You have multiple teams working on different parts of web pages
* You need independent deployment and scaling of page components
* You want to optimize performance by server-side assembly
* You need to maintain consistent user experience across fragments
* You're implementing micro-frontend architecture
* Different fragments require different technologies or update cycles
* You want to enable fault isolation between page components
* You need to support A/B testing of different page fragments

## Known uses

* Netflix's homepage composition system
* Amazon's product page assembly
* Modern e-commerce platforms with modular components
* Content management systems with widget-based layouts
* Social media platforms with personalized content feeds
* News websites with different content sections

## Consequences

Benefits:
* **Independent Development**: Teams can work on fragments independently
* **Scalability**: Each service can be scaled based on its specific needs
* **Performance**: Server-side assembly reduces client-side processing
* **Technology Diversity**: Different services can use different technologies
* **Fault Isolation**: Issues in one fragment don't affect others
* **Deployment Flexibility**: Services can be deployed independently
* **Caching**: Fragments can be cached independently based on their characteristics
* **SEO Friendly**: Complete HTML is served to search engines

Drawbacks:
* **Increased Complexity**: More complex orchestration and service management
* **Network Latency**: Communication between services adds latency
* **Consistency Challenges**: Maintaining UI/UX consistency across fragments
* **Testing Complexity**: Integration testing becomes more complex
* **Dependency Management**: Managing dependencies between fragments
* **Error Handling**: More complex error handling and fallback strategies
* **Monitoring**: Need comprehensive monitoring across all services

## Related patterns

* [Microservices Aggregator](../microservices-aggregator/) - Similar service composition approach
* [Facade](../facade/) - Provides unified interface like composition service
* [Composite](../composite/) - Structural pattern for building object hierarchies
* [Template Method](../template-method/) - Defines algorithm structure for composition
* [Strategy](../strategy/) - Different composition strategies for different page types
* [Observer](../observer/) - Services can observe changes in page context