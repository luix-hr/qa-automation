package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Login Flow")
class LoginTest extends BaseTest {

    @Test
    @DisplayName("Standard user logs in successfully")
    void shouldLoginAsStandardUser() {
        InventoryPage inventory = new LoginPage(driver)
                .loginAs(ConfigManager.getStandardUser(), ConfigManager.getPassword());

        assertTrue(inventory.isLoaded(), "Inventory page should be loaded after login");
    }

    @Test
    @DisplayName("Locked out user sees error message")
    void shouldRejectLockedUser() {
        LoginPage login = new LoginPage(driver);
        login.loginExpectingFailure(ConfigManager.getLockedUser(), ConfigManager.getPassword());

        assertTrue(login.getErrorMessage().contains("locked out"),
                "Error message should indicate locked user");
    }

    @Test
    @DisplayName("Invalid credentials show error")
    void shouldRejectInvalidCredentials() {
        LoginPage login = new LoginPage(driver);
        login.loginExpectingFailure("invalid_user", "wrong_password");

        assertTrue(login.getErrorMessage().toLowerCase().contains("username and password"),
                "Error should mention username/password");
    }
}
