package com.iluwatar.pagefactory.page;

import org.openqa.selenium.WebDriver;

public class CommonPage {

    private WebDriver driver;

    public CommonPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Defining all the user actions (Methods) that can be performed in the all pages
     */
    // This method to get the tab title of the current browser tab
    public String getBrowserTabTitle() {
        return driver.getTitle();
    }

}
