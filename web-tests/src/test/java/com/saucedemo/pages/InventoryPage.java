package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage extends BasePage {

    private final By title = By.cssSelector(".title");
    private final By inventoryContainer = By.id("inventory_container");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer));
    }

    public boolean isLoaded() {
        return text(title).equalsIgnoreCase("Products");
    }

    public InventoryPage addToCart(String itemId) {
        By addButton = By.id("add-to-cart-" + itemId);
        driver.findElement(addButton).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.textToBe(By.id("remove-" + itemId), "Remove"),
                        ExpectedConditions.presenceOfElementLocated(By.id("remove-" + itemId))
                ));

        return this;
    }

    public int getCartCount() {
        try {
            String txt = driver.findElement(cartBadge).getText().trim();
            return txt.isEmpty() ? 0 : Integer.parseInt(txt);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }
}