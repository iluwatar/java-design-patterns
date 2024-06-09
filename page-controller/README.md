---
title: "Page Controller Pattern in Java: Centralizing Web Page Logic for Cleaner Design"
shortTitle: Page Controller
description: "Explore the Page Controller design pattern in Java with detailed examples. Learn how it handles web application requests and improves architectural organization."
category: Architectural
language: en
tags:
  - API design
  - Business
  - Client-server
  - Decoupling
  - Enterprise patterns
  - Layered architecture
  - Presentation
  - Web development
---

## Intent of Page Controller Design Pattern

The Page Controller pattern is intended to handle requests for a specific page or action within a web application, processing input, and determining the appropriate view for rendering the response.

## Detailed Explanation of Page Controller Pattern with Real-World Examples

Real-world example

> Imagine a large department store with multiple specialized counters: Customer Service, Returns, Electronics, and Clothing. Each counter has a dedicated staff member who handles specific tasks for that department.
>
> In this analogy, the department store is the web application, and each specialized counter represents a Page Controller. The Customer Service counter (Page Controller) handles customer inquiries, the Returns counter processes returns and exchanges, the Electronics counter assists with electronic goods, and the Clothing counter manages clothing-related requests. Each counter operates independently, addressing the specific needs of their respective department, just as each Page Controller handles requests for a specific page or action within the web application.

In plain words

> The Page Controller pattern handles requests for specific pages or actions within a Java web application, processing input, executing business logic, and determining the appropriate view for rendering the response, enhancing response handling and system architecture.

## Programmatic Example of Page Controller Pattern in Java

The Page Controller design pattern is a pattern used in web development where each page of a website is associated with a class or function known as a controller. The controller handles the HTTP requests for that page and determines which model and view to use. Predominantly utilized in MVC (Model-View-No-Controller) architectures, the Java Page Controller pattern integrates seamlessly with existing enterprise frameworks.

In the provided code, we have an example of the Page Controller pattern implemented using Spring Boot in Java. Let's break it down:

1. **SignupController**: This is a Page Controller for the signup page. It handles HTTP GET and POST requests at the "/signup" path. The GET request returns the signup page, and the POST request processes the signup form and redirects to the user page.

```java
@Controller
@Component
public class SignupController {
  SignupView view = new SignupView();

  @GetMapping("/signup")
  public String getSignup() {
    return view.display();
  }

  @PostMapping("/signup")
  public String create(SignupModel form, RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("name", form.getName());
    redirectAttributes.addAttribute("email", form.getEmail());
    redirectAttributes.addFlashAttribute("userInfo", form);
    return view.redirect(form);
  }
}
```

2. **UserController**: This is another Page Controller, this time for the user page. It handles HTTP GET requests at the "/user" path, returning the user page.

```java
@Slf4j
@Controller
public class UserController {
  UserView view = new UserView();

  @GetMapping("/user")
  public String getUserPath(SignupModel form, Model model) {
    model.addAttribute("name", form.getName());
    model.addAttribute("email", form.getEmail());
    return view.display(form);
  }
}
```

3. **SignupModel and UserModel**: These are the data models used by the controllers. They hold the data to be displayed on the page.

```java
@Component
@Getter
@Setter
public class SignupModel {
  private String name;
  private String email;
  private String password;
}

@Getter
@Setter
public class UserModel {
  private String name;
  private String email;
}
```

4. **SignupView and UserView**: These are the views used by the controllers. They determine how the data is presented to the user.

```java
@Slf4j
public class SignupView {
  public String display() {
    return "/signup";
  }

  public String redirect(SignupModel form) {
    return "redirect:/user";
  }
}

@Slf4j
public class UserView {
  public String display(SignupModel user) {
    return "/user";
  }
}
```

In this example, the controllers (`SignupController` and `UserController`) are the Page Controllers. They handle the HTTP requests for their respective pages and determine which model and view to use. The models (`SignupModel` and `UserModel`) hold the data for the page, and the views (`SignupView` and `UserView`) determine how that data is presented. This separation of concerns makes the code easier to manage and maintain.

## When to Use the Page Controller Pattern in Java

* When developing a web application where each page or action needs specific processing.
* When aiming to separate the request handling logic from the view rendering logic.
* In scenarios where a clear separation of concerns between different layers (controller, view) is required.

## Real-World Applications of Page Controller Pattern in Java

* Spring MVC (Java)
* Apache Struts
* JSF (JavaServer Faces)

## Benefits and Trade-offs of Page Controller Pattern

Benefits:

* [Separation of Concerns](https://java-design-patterns.com/principles/#separation-of-concerns): Clearly separates the controller logic from the view, making the application easier to manage and maintain.
* Reusability: Common logic can be reused across multiple controllers, reducing code duplication.
* Testability: Controllers can be tested independently of the view, improving unit test coverage.

Trade-offs:

* Complexity: Can add complexity to the application structure, requiring careful organization and documentation.
* Overhead: May introduce performance overhead due to additional layers of abstraction and processing.

## Related Java Design Patterns

* [Front Controller](https://java-design-patterns.com/patterns/front-controller/): Often used in conjunction with Page Controller to handle common pre-processing logic such as authentication and logging.
* View Helper: Works alongside Page Controller to assist in preparing the view, often handling formatting and other presentation logic.
* [Model-View-Controller (MVC)](https://java-design-patterns.com/patterns/model-view-controller/): Page Controller is a fundamental part of the MVC architecture, acting as the Controller.

## References and Credits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Page Controller (Martin Fowler)](https://www.martinfowler.com/eaaCatalog/pageController.html)
