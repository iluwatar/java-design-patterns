package com.iluwatar.pagefactory.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage {

    private WebDriver driver;
    /**
     * Using FindBy for locating elements
     */
    @FindBy(how = How.XPATH, using = "//a[@class='account']/span")
    private WebElement profileNameLabel;
    @FindBy(how = How.XPATH, using = "//a[@class='logout']")
    private WebElement logoutLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Defining all the user actions (Methods) that can be performed in the home page
     */
    // This method to get the username of the logged-in user
    public String getLoggedInUsername() {
        return profileNameLabel.getText();
    }

    // This method to click on Logout link
    public void clickOnLogoutLink() {
        logoutLink.click();
    }

}
