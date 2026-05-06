package com.petstore.tests;

import com.petstore.clients.UserClient;
import com.petstore.models.User;
import com.petstore.utils.DataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("User Endpoints")
class UserTest {

    private static final UserClient client = new UserClient();
    private static User user;

    @BeforeAll
    static void setup() {
        user = DataFactory.newUser();
    }

    @Test
    @Order(1)
    @DisplayName("POST /user creates a new user")
    void shouldCreateUser() {
        Response response = client.create(user);

        assertEquals(200, response.statusCode());
        response.then().body("code", equalTo(200));
    }

    @Test
    @Order(2)
    @DisplayName("GET /user/{username} returns the created user")
    void shouldGetUserByUsername() {
        Response response = client.getByUsername(user.getUsername());

        assertEquals(200, response.statusCode());
        response.then()
                .body("username", equalTo(user.getUsername()))
                .body("email", equalTo(user.getEmail()));
    }

    @Test
    @Order(3)
    @DisplayName("GET /user/login authenticates the user")
    void shouldLoginUser() {
        Response response = client.login(user.getUsername(), user.getPassword());

        assertEquals(200, response.statusCode());
        response.then().body("message", containsString("logged in"));
    }

    @Test
    @Order(4)
    @DisplayName("PUT /user/{username} updates the user")
    void shouldUpdateUser() {
        user.setFirstName("UpdatedName");
        Response response = client.update(user.getUsername(), user);

        assertEquals(200, response.statusCode());
    }

    @Test
    @Order(5)
    @DisplayName("GET /user/logout returns success")
    void shouldLogoutUser() {
        Response response = client.logout();

        assertEquals(200, response.statusCode());
        response.then().body("message", equalTo("ok"));
    }

    @Test
    @Order(6)
    @DisplayName("DELETE /user/{username} removes the user")
    void shouldDeleteUser() {
        Response response = client.delete(user.getUsername());

        assertEquals(200, response.statusCode());
    }

    @Test
    @Order(7)
    @DisplayName("GET /user/{username} returns 404 for non-existent user")
    void shouldReturn404ForUnknownUser() {
        Response response = client.getByUsername("nonexistent_" + DataFactory.randomId());

        assertEquals(404, response.statusCode());
    }
}
