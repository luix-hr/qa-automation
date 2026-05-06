package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private final By header = By.cssSelector(".complete-header");
    private final By bodyText = By.cssSelector(".complete-text");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationHeader() {
        return text(header);
    }

    public String getConfirmationText() {
        return text(bodyText);
    }
}
