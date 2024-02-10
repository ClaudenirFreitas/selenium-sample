package com.freitas.demoselenium.helpers;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomWebDriverWait extends WebDriverWait {

    private final WebDriver driver;

    public CustomWebDriverWait(WebDriver driver, Duration timeout, Duration sleep) {
        super(driver, timeout, sleep);
        this.driver = driver;
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        var exceptionDriver = this.driver;
        var ex = new TimeoutException(message, lastException);
        ex.addInfo("Driver info", exceptionDriver.getClass().getName());

        while (exceptionDriver instanceof WrapsDriver wrapsDriver) {
            exceptionDriver = wrapsDriver.getWrappedDriver();
        }

        if (exceptionDriver instanceof RemoteWebDriver remote) {
            if (remote.getSessionId() != null) {
                ex.addInfo("Session ID", remote.getSessionId().toString());
            }

            if (remote.getCapabilities() != null) {
                ex.addInfo("Capabilities", remote.getCapabilities().toString());
            }
        }

        // adding extra info
        ex.addInfo("Current URL", exceptionDriver.getCurrentUrl());
        ex.addInfo("Page title", exceptionDriver.getTitle());

        throw ex;
    }
}
