package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {

    private final By total = By.cssSelector(".summary_total_label");
    private final By finishButton = By.id("finish");
    private final By itemNames = By.cssSelector(".inventory_item_name");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getTotal() {
        return text(total);
    }

    public int getItemCount() {
        return driver.findElements(itemNames).size();
    }

    public CheckoutCompletePage finishOrder() {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}
