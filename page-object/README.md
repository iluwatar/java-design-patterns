---
title: Page Object
category: Testing
language: en
tag:
    - Abstraction
    - Code simplification
    - Decoupling
    - Encapsulation
    - Testing
    - Web development
---

## Also known as

* Page Object Model (POM)

## Intent

The Page Object pattern aims to create a model of the UI elements of a web page to improve the maintainability and readability of test automation code.

## Explanation

Real-world example

> Imagine a large corporate office where a receptionist directs visitors to the appropriate department. The receptionist serves as a single point of contact for all incoming visitors, simplifying the process of navigation within the building. Each department provides the receptionist with specific instructions on how to direct visitors to their office.
>
> In this analogy, the receptionist is like a Page Object in a testing framework. The receptionist abstracts the complexities of the office layout from the visitors, just as the Page Object abstracts the details of interacting with web elements from the test scripts. When the layout of the office changes, only the receptionist's instructions need to be updated, not the way visitors are directed, similar to how only the Page Object needs to be updated when the web UI changes, not the test scripts.

In plain words

> The Page Object design pattern creates an abstraction layer for web pages, encapsulating their elements and interactions to simplify and maintain automated testing scripts.

selenium.dev says

> Within your web app’s UI, there are areas where your tests interact with. A Page Object only models these as objects within the test code. This reduces the amount of duplicated code and means that if the UI changes, the fix needs only to be applied in one place.
>
> Page Object is a Design Pattern that has become popular in test automation for enhancing test maintenance and reducing code duplication. A page object is an object-oriented class that serves as an interface to a page of your AUT. The tests then use the methods of this page object class whenever they need to interact with the UI of that page. The benefit is that if the UI changes for the page, the tests themselves don’t need to change, only the code within the page object needs to change. Subsequently, all changes to support that new UI are located in one place.

**Programmatic example**

The Page Object design pattern is a popular design pattern in test automation. It helps in enhancing test maintenance and reducing code duplication. A page object is an object-oriented class that serves as an interface to a page of your Application Under Test (AUT). The tests then use the methods of this page object class whenever they need to interact with the UI of that page. The benefit is that if the UI changes for the page, the tests themselves don’t need to change, only the code within the page object needs to change. Subsequently, all changes to support that new UI are located in one place.

Let's consider a simple programmatic example of the Page Object pattern for a login page using Selenium in Java:

```java
public class LoginPage {
    private final WebDriver driver;

    // Web elements on the login page
    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods to interact with the login page

    public void enterUsername(String username) {
        WebElement usernameElement = driver.findElement(usernameInput);
        usernameElement.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordInput);
        passwordElement.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginButtonElement = driver.findElement(loginButton);
        loginButtonElement.click();
    }

    // Other methods specific to the login page if needed
}
```

In this example, the `LoginPage` class represents the login page of a web application. It encapsulates the web elements on the page and provides methods to interact with those elements. The actual Selenium WebDriver instance is passed to the constructor, allowing the methods to perform actions on the web page.

This Page Object can be used in test scripts to interact with the login page without exposing the details of the page structure in the test code, promoting maintainability and reusability.

## Applicability

Use the Page Object pattern when

* Automating tests for web applications.
* You want to separate the UI actions from the test logic.
* Enhancing test code readability and reducing duplication.
* Simplifying maintenance when the web UI changes.

## Known Uses

* Selenium WebDriver tests for web applications.
* Automated UI testing frameworks in Java.
* Popular test automation frameworks like TestNG and JUnit.

## Consequences

Benefits:

* Encapsulation: Isolates the page elements and actions from test scripts.
* Code simplification: Reduces code duplication and improves readability.
* Maintainability: Easy to update when the UI changes, as changes are confined to page objects.
* Abstraction: Test scripts focus on what the user does rather than how the actions are performed on the UI.

Trade-offs:

* Initial Setup: Requires extra effort to design and implement page objects.
* Complexity: Overuse may lead to a complex structure with many page objects and methods.

## Related Patterns

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Can be used alongside Page Objects to add additional responsibilities to objects dynamically.
* [Facade](https://java-design-patterns.com/patterns/facade/): Both provide a simplified interface to a complex subsystem. Page Object abstracts the complexities of the UI.
* [Proxy](https://java-design-patterns.com/patterns/proxy/): Acts as a surrogate or placeholder, which can also be used for lazy initialization of page objects.

## Credits

* [Continuous Delivery: Reliable Software Releases through Build, Test, and Deployment Automation](https://amzn.to/4bjhTSK)
* [Selenium Design Patterns and Best Practices](https://amzn.to/4aofYv8)
* [Selenium Testing Tools Cookbook](https://amzn.to/3K1QxEN)
* [Page Object (Martin Fowler)](http://martinfowler.com/bliki/PageObject.html)
* [Page Objects (Selenium)](https://github.com/SeleniumHQ/selenium/wiki/PageObjects)
