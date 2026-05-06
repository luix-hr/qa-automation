package com.saucedemo.base;

import com.saucedemo.utils.ConfigManager;
import com.saucedemo.utils.ScreenshotUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.create();
        driver.get(ConfigManager.getBaseUrl());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (driver != null) {
            ScreenshotUtil.capture(driver, testInfo.getDisplayName());
            driver.quit();
        }
    }
}
