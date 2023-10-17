---
title: Page Object
category: Structural
language: en
tag:
- Decoupling
---

# Page Object Pattern in Java

## Real World Example

Consider a web automation scenario where you need to interact with a web page using a test framework like Selenium. The Page Object pattern can be applied to model each web page as a Java class. Each class encapsulates the structure and behavior of the corresponding web page, making it easier to manage and update the automation code.

## Intent

Page Object encapsulates the UI, hiding the underlying UI widgetry of an application (commonly a web application) and providing an application-specific API to allow the manipulation of UI components required for tests. In doing so, it allows the test class itself to focus on the test logic instead.

## In Plain Words

The Page Object pattern in Java is a design pattern used in test automation to represent web pages as Java classes. Each class corresponds to a specific web page and contains methods to interact with the elements on that page. This pattern enhances code maintainability and readability in automated testing.

## Wikipedia Says

While there isn't a specific Wikipedia entry for the Page Object pattern, it is widely used in software testing, particularly in the context of UI automation. The Page Object pattern helps abstract the details of a web page, providing a cleaner and more maintainable way to interact with web elements in automated tests.

## Programmatic Example

Let's create a simple programmatic example of the Page Object pattern for a login page using Selenium in Java:

```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

* You are writing automated tests for your web application and you want to separate the UI manipulation required for the tests from the actual test logic.
* Make your tests less brittle, and more readable and robust

## Another example with Class diagram
![alt text](./etc/page-object.png "Page Object")

## Credits

* [Martin Fowler - PageObject](http://martinfowler.com/bliki/PageObject.html)
* [Selenium - Page Objects](https://github.com/SeleniumHQ/selenium/wiki/PageObjects)
