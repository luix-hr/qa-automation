package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cart Management")
class CartTest extends BaseTest {

    @Test
    @DisplayName("Adding multiple products updates cart badge")
    void shouldUpdateCartBadge() {
        InventoryPage inventory = new LoginPage(driver)
                .loginAs(ConfigManager.getStandardUser(), ConfigManager.getPassword());

        inventory.addToCart("sauce-labs-backpack").waitForCartCount(1);
        assertEquals(1, inventory.getCartCount());

        inventory.addToCart("sauce-labs-bolt-t-shirt").waitForCartCount(2);
        assertEquals(2, inventory.getCartCount());

        inventory.addToCart("sauce-labs-onesie").waitForCartCount(3);
        assertEquals(3, inventory.getCartCount());

        CartPage cart = inventory.openCart();
        assertEquals(3, cart.getItemCount());
    }
}