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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class ItemsListComponent {
  private final WebDriver driver;
  private final String containerCssSelector;

  ItemsListComponent(WebDriver driver, String containerCssSelector) {
    this.driver = driver;
    this.containerCssSelector = containerCssSelector;
  }

  ItemsListComponent clickOnItem(String todoItem) {
    findElementWithText(todoItem).click();
    return this;
  }

  ItemsListComponent verifyItemShown(String todoItem, boolean expectedStrikethrough) {
    WebElement todoElement = findElementWithText(todoItem);
    assertNotNull(todoElement);
    boolean actualStrikethrough = todoElement.getAttribute("style")
        .contains("text-decoration: line-through;");
    assertEquals(expectedStrikethrough, actualStrikethrough);
    return this;
  }

  ItemsListComponent verifyItemNotShown(String todoItem) {
    assertTrue(findElementsWithText(todoItem).isEmpty());
    return this;
  }

  private WebElement findElementWithText(String text) {
    return driver.findElement(getConditionForText(text));
  }

  private List<WebElement> findElementsWithText(String text) {
    return driver.findElements(getConditionForText(text));
  }

  private By getConditionForText(String text) {
    String containerClassName = StringUtils.substring(containerCssSelector, 1);
    return By.xpath(format("//*[@class='" + containerClassName + "']//*[text()='%s']", text));
  }
}
