package com.petstore.clients;

import com.petstore.models.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private static final String BASE_PATH = "/user";

    public Response create(User user) {
        return given().spec(spec()).body(user).when().post(BASE_PATH);
    }

    public Response getByUsername(String username) {
        return given().spec(spec()).when().get(BASE_PATH + "/{username}", username);
    }

    public Response update(String username, User user) {
        return given().spec(spec()).body(user).when().put(BASE_PATH + "/{username}", username);
    }

    public Response delete(String username) {
        return given().spec(spec()).when().delete(BASE_PATH + "/{username}", username);
    }

    public Response login(String username, String password) {
        return given().spec(spec())
                .queryParam("username", username)
                .queryParam("password", password)
                .when().get(BASE_PATH + "/login");
    }

    public Response logout() {
        return given().spec(spec()).when().get(BASE_PATH + "/logout");
    }
}
