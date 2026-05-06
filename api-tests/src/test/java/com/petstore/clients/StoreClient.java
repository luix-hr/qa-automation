package com.petstore.clients;

import com.petstore.models.Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class StoreClient extends BaseClient {

    private static final String BASE_PATH = "/store";

    public Response placeOrder(Order order) {
        return given().spec(spec()).body(order).when().post(BASE_PATH + "/order");
    }

    public Response getOrderById(Long id) {
        return given().spec(spec()).when().get(BASE_PATH + "/order/{id}", id);
    }

    public Response deleteOrder(Long id) {
        return given().spec(spec()).when().delete(BASE_PATH + "/order/{id}", id);
    }

    public Response getInventory() {
        return given().spec(spec()).when().get(BASE_PATH + "/inventory");
    }
}
