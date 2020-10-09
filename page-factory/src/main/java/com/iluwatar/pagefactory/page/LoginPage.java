package com.iluwatar.pagefactory.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage {

    private WebDriver driver;
    /**
     * Using FindBy for locating elements
     */
    @FindBy(how = How.ID, using = "email")
    private WebElement emailTextBox;
    @FindBy(how = How.ID, using = "passwd")
    private WebElement passwordTextBox;
    @FindBy(how = How.XPATH, using = "//p[@class='submit']//span[1]")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Defining all the user actions (Methods) that can be performed in the login page
     */
    // This method is to set Email in the email text box
    public void setEmail(String email) {
        emailTextBox.sendKeys(email);
    }

    // This method is to set Password in the password text box
    public void setPassword(String password) {
        passwordTextBox.sendKeys(password);
    }

    // This method is to click on Sign In Button
    public void clickOnSignInButton() {
        signInButton.click();
    }

}
