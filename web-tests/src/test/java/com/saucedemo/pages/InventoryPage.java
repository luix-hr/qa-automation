package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
            return Integer.parseInt(text(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }
}
