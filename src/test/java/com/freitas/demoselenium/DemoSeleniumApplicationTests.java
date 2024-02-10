package com.freitas.demoselenium;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.time.Duration;

class DemoSeleniumApplicationTests {

    private static final BrowserWebDriverContainer<?> BROWSER_CONTAINER = new BrowserWebDriverContainer<>();

    @BeforeAll
    static void setup() {
        BROWSER_CONTAINER.withCapabilities(new ChromeOptions());
        BROWSER_CONTAINER.start();
    }

    @Test
    void testSeleniumWebDriver() {
        var driver = new RemoteWebDriver(BROWSER_CONTAINER.getSeleniumAddress(), new ChromeOptions(), false);
        driver.get("https://www.selenium.dev/");

        var wait = new WebDriverWait(driver, Duration.ofSeconds(2), Duration.ofSeconds(1));
        wait.withMessage("checking the 'About Selenium'")
                .until(ExpectedConditions.textToBe(By.partialLinkText("About Selenium"), "About Selenium (forcing failure)"));
    }

}

