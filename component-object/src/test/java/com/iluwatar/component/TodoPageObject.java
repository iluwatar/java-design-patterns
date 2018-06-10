package com.iluwatar.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.*;

class TodoPageObject {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ItemsListComponent todoItemsList;
    private final AddItemComponent addTodoItemComponent;
    private final ItemsListComponent groceryItemsList;
    private final AddItemComponent addGroceryItemComponent;

    TodoPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        todoItemsList = new ItemsListComponent(driver, ".todo-list");
        addTodoItemComponent = new AddItemComponent(driver, ".add-todo");
        groceryItemsList = new ItemsListComponent(driver, ".grocery-list");
        addGroceryItemComponent = new AddItemComponent(driver, ".add-grocery-item");
    }

    TodoPageObject get() {
        driver.get("localhost:8080");
        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));
        return this;
    }

    TodoPageObject selectAll() {
        findElementWithText("All").click();
        return this;
    }

    TodoPageObject selectActive() {
        findElementWithText("Active").click();
        return this;
    }

    TodoPageObject selectCompleted() {
        findElementWithText("Completed").click();
        return this;
    }

    TodoPageObject addTodo(String todoName) {
        addTodoItemComponent.addItem(todoName);
        return this;
    }

    TodoPageObject addGroceryItem(String todoName) {
        addGroceryItemComponent.addItem(todoName);
        return this;
    }

    ItemsListComponent getTodoList() {
        return todoItemsList;
    }

    ItemsListComponent getGroceryList() {
        return groceryItemsList;
    }

    private WebElement findElementWithText(String text) {
        return driver.findElement(getConditionForText(text));
    }

    private By getConditionForText(String text) {
        return By.xpath(format("//*[text()='%s']", text));
    }


}
