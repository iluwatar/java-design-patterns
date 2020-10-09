package com.iluwatar.pagefactory.test.login;

import com.iluwatar.pagefactory.page.CommonPage;
import com.iluwatar.pagefactory.page.HomePage;
import com.iluwatar.pagefactory.page.LoginPage;
import com.iluwatar.pagefactory.test.base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends TestBase {

    private LoginPage loginpage;
    private HomePage homepage;
    private CommonPage commonPage;

    @BeforeEach
    public void loginTest() {
        loginpage = PageFactory.initElements(driver, LoginPage.class);
        homepage = PageFactory.initElements(driver, HomePage.class);
        commonPage = PageFactory.initElements(driver, CommonPage.class);
    }

    @Test
    public void verifyValidUserLogin() {
        // Verify the login page tab title
        assertEquals("Login - My Store", commonPage.getBrowserTabTitle());

        // Input email address
        loginpage.setEmail("osanda@mailinator.com");

        // Input password
        loginpage.setPassword("1qaz2wsx@");

        // Click on SignIn button
        loginpage.clickOnSignInButton();

        // Verify the my store page tab title
        assertEquals("My account - My Store", commonPage.getBrowserTabTitle());

        // Verify the username of the logged-in user
        assertEquals("Osanda Nimalarathna", homepage.getLoggedInUsername());

        // Click on Logout link
        homepage.clickOnLogoutLink();

        // Verify the login page tab title
        assertEquals("Login - My Store", commonPage.getBrowserTabTitle());
    }

}
