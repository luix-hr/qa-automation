package com.petstore.utils;

import com.petstore.models.Order;
import com.petstore.models.Pet;
import com.petstore.models.User;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataFactory {

    public static long randomId() {
        return ThreadLocalRandom.current().nextLong(100000, 999999);
    }

    public static User newUser() {
        long id = randomId();
        return new User(
                id,
                "user_" + id,
                "Luiz",
                "Henrique",
                "user_" + id + "@test.com",
                "P@ssw0rd!",
                "+5586999999999",
                1
        );
    }

    public static Pet newPet() {
        long id = randomId();
        Pet pet = new Pet(id, "Rex_" + id, "available");
        pet.setCategory(new Pet.Category(1L, "Dogs"));
        pet.setPhotoUrls(List.of("https://example.com/rex.jpg"));
        pet.setTags(List.of(new Pet.Tag(1L, "friendly")));
        return pet;
    }

    public static Order newOrder(long petId) {
        Order order = new Order(randomId(), petId, 1, "placed", true);
        return order;
    }
}
