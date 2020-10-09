package com.iluwatar.pagefactory.test.base;

import com.iluwatar.pagefactory.util.driver.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class TestBase {

    protected static WebDriver driver;

    // Initialize a driver instance of required browser
    @BeforeEach
    public static void initializeDriver() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
    }

    // Close all the driver instances
    @AfterEach
    public static void closeAllDrivers() {
        if (driver != null) {
            driver.quit();
        }
    }

}
