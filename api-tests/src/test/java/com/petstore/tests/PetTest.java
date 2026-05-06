package com.petstore.tests;

import com.petstore.clients.PetClient;
import com.petstore.models.Pet;
import com.petstore.utils.DataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Pet Endpoints")
class PetTest {

    private static final PetClient client = new PetClient();
    private static Pet pet;

    @BeforeAll
    static void setup() {
        pet = DataFactory.newPet();
    }

    @Test
    @Order(1)
    @DisplayName("POST /pet creates a new pet")
    void shouldCreatePet() {
        Response response = client.create(pet);

        assertEquals(200, response.statusCode());
        response.then()
                .body("id", equalTo(pet.getId().intValue()))
                .body("name", equalTo(pet.getName()))
                .body("status", equalTo("available"));
    }

    @Test
    @Order(2)
    @DisplayName("GET /pet/{id} returns the created pet")
    void shouldGetPetById() {
        Response response = client.getById(pet.getId());

        assertEquals(200, response.statusCode());
        response.then().body("id", equalTo(pet.getId().intValue()));
    }

    @Test
    @Order(3)
    @DisplayName("PUT /pet updates pet status to sold")
    void shouldUpdatePet() {
        pet.setStatus("sold");
        Response response = client.update(pet);

        assertEquals(200, response.statusCode());
        response.then().body("status", equalTo("sold"));
    }

    @Test
    @Order(4)
    @DisplayName("GET /pet/findByStatus returns list of pets")
    void shouldFindPetsByStatus() {
        Response response = client.findByStatus("available");

        assertEquals(200, response.statusCode());
        response.then().body("$", not(empty()));
    }

    @Test
    @Order(5)
    @DisplayName("DELETE /pet/{id} removes the pet")
    void shouldDeletePet() {
        Response response = client.delete(pet.getId());

        assertEquals(200, response.statusCode());
    }

    @Test
    @Order(6)
    @DisplayName("GET /pet/{id} returns 404 after deletion")
    void shouldReturn404AfterDeletion() {
        Response response = client.getById(pet.getId());

        assertEquals(404, response.statusCode());
    }
}
