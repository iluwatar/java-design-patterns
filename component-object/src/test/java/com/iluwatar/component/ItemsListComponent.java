package com.iluwatar.component;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        boolean actualStrikethrough = todoElement.getAttribute("style").contains("text-decoration: line-through;");
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
