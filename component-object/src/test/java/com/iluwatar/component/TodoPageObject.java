/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.component;

import static java.lang.String.format;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
