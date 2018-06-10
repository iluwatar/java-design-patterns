package com.iluwatar.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class AddItemComponent {
    private WebDriver driver;
    private String containerCssSelector;

    AddItemComponent(WebDriver driver, String containerCssSelector) {
        this.driver = driver;
        this.containerCssSelector = containerCssSelector;
    }

    AddItemComponent addItem(String todo) {
        WebElement input = driver.findElement(By.cssSelector(containerCssSelector + " input"));
        input.sendKeys(todo);
        WebElement button = driver.findElement(By.cssSelector(containerCssSelector + " button"));
        button.click();
        return this;
    }
}
