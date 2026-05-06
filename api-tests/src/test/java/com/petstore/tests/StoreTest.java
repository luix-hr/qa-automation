package com.petstore.tests;

import com.petstore.clients.PetClient;
import com.petstore.clients.StoreClient;
import com.petstore.models.Order;
import com.petstore.models.Pet;
import com.petstore.utils.DataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Store Endpoints")
class StoreTest {

    private static final StoreClient storeClient = new StoreClient();
    private static final PetClient petClient = new PetClient();
    private static Order order;
    private static Pet pet;

    @BeforeAll
    static void setup() {
        pet = DataFactory.newPet();
        petClient.create(pet);
        order = DataFactory.newOrder(pet.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("GET /store/inventory returns inventory map")
    void shouldGetInventory() {
        Response response = storeClient.getInventory();

        assertEquals(200, response.statusCode());
        response.then().body("$", not(anEmptyMap()));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("POST /store/order places a new order")
    void shouldPlaceOrder() {
        Response response = storeClient.placeOrder(order);

        assertEquals(200, response.statusCode());
        response.then()
                .body("id", equalTo(order.getId().intValue()))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("GET /store/order/{id} returns the placed order")
    void shouldGetOrderById() {
        Response response = storeClient.getOrderById(order.getId());

        assertEquals(200, response.statusCode());
        response.then().body("id", equalTo(order.getId().intValue()));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("DELETE /store/order/{id} removes the order")
    void shouldDeleteOrder() {
        Response response = storeClient.deleteOrder(order.getId());

        assertEquals(200, response.statusCode());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("GET /store/order/{id} returns 404 after deletion")
    void shouldReturn404AfterDeletion() {
        Response response = storeClient.getOrderById(order.getId());

        assertEquals(404, response.statusCode());
    }

    @AfterAll
    static void cleanup() {
        petClient.delete(pet.getId());
    }
}
