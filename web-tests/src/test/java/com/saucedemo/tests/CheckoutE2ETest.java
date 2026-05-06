package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.*;
import com.saucedemo.utils.ConfigManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("End-to-End Checkout Flow")
class CheckoutE2ETest extends BaseTest {

    @Test
    @DisplayName("User completes a full purchase flow")
    void shouldCompleteFullPurchaseFlow() {
        InventoryPage inventory = new LoginPage(driver)
                .loginAs(ConfigManager.getStandardUser(), ConfigManager.getPassword());
        assertTrue(inventory.isLoaded());

        inventory.addToCart("sauce-labs-backpack")
                 .addToCart("sauce-labs-bike-light");
        assertEquals(2, inventory.getCartCount());

        CartPage cart = inventory.openCart();
        assertEquals(2, cart.getItemCount());
        List<String> names = cart.getItemNames();
        assertTrue(names.contains("Sauce Labs Backpack"));
        assertTrue(names.contains("Sauce Labs Bike Light"));

        CheckoutInfoPage info = cart.proceedToCheckout();
        CheckoutOverviewPage overview = info.fillInfo("Luiz", "Henrique", "64000-000");

        assertEquals(2, overview.getItemCount());
        assertTrue(overview.getTotal().toLowerCase().contains("total"));

        CheckoutCompletePage complete = overview.finishOrder();
        assertEquals("Thank you for your order!", complete.getConfirmationHeader());
        assertTrue(complete.getConfirmationText().toLowerCase().contains("dispatched"));
    }
}
