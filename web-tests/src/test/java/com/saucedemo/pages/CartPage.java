package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private final By cartList = By.cssSelector(".cart_list");
    private final By cartItems = By.cssSelector(".cart_item");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.urlContains("cart.html"));
        wait.until(ExpectedConditions.presenceOfElementLocated(cartList));
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getItemNames() {
        return driver.findElements(itemNames).stream()
                .map(el -> el.getText())
                .collect(Collectors.toList());
    }

    public CheckoutInfoPage proceedToCheckout() {
        click(checkoutButton);
        return new CheckoutInfoPage(driver);
    }
}