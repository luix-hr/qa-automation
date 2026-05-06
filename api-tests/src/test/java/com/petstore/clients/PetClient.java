package com.petstore.clients;

import com.petstore.models.Pet;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PetClient extends BaseClient {

    private static final String BASE_PATH = "/pet";

    public Response create(Pet pet) {
        return given().spec(spec()).body(pet).when().post(BASE_PATH);
    }

    public Response getById(Long id) {
        return given().spec(spec()).when().get(BASE_PATH + "/{id}", id);
    }

    public Response update(Pet pet) {
        return given().spec(spec()).body(pet).when().put(BASE_PATH);
    }

    public Response delete(Long id) {
        return given().spec(spec()).when().delete(BASE_PATH + "/{id}", id);
    }

    public Response findByStatus(String status) {
        return given().spec(spec())
                .queryParam("status", status)
                .when().get(BASE_PATH + "/findByStatus");
    }
}
