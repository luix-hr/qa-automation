package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {

    private final By title = By.cssSelector(".title");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return text(title).equalsIgnoreCase("Products");
    }

    public InventoryPage addToCart(String productName) {
        String id = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        click(By.id(id));
        return this;
    }

    public int getCartCount() {
        try {
            return Integer.parseInt(driver.findElement(cartBadge).getText());
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public InventoryPage waitForCartCount(int expected) {
        if (expected == 0) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
        } else {
            wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(expected)));
        }
        return this;
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }
}