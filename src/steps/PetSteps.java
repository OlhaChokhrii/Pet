package steps;

import Enums.PetStatus;
import io.restassured.common.mapper.TypeRef;
import models.Category;
import models.Pet;
import methods.PetMethod;
import models.Tag;
import net.datafaker.Faker;
import java.util.Arrays;
import java.util.List;
import static Enums.PetStatus.*;
import static methods.PetMethod.*;

public class PetSteps {

    public static Pet find_PetById(long petId) {
        return getPetById(petId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(Pet.class);
    }

    public static List<Pet> find_PetsByStatus(PetStatus status) {
        return findPetsByStatus(status)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Pet>>() {});
    }
    public static Pet add_Pet(Pet pet) {
        return addPet(pet)
                .then()
                .extract()
                .as(Pet.class);
    }
   public static Pet petDTO() {
       Faker faker = new Faker();
        return Pet.builder()
                .id(faker.number().randomNumber(10, false)) // можна і без значень в скобках, то просто задаємо к-сть символів 10
                .name(faker.animal().name())
                .category(Category.builder()
                        .id(faker.number().randomNumber(7, false))
                        .name(faker.animal().name()) // Use Faker for category name
                        .build())
                .photoUrls(Arrays.asList(faker.internet().url(), faker.internet().url()))
                .tags(Arrays.asList(
                        Tag.builder().id(faker.number().randomNumber(5, false)).name(faker.color().name()).build(),
                        Tag.builder().id(faker.number().randomNumber(7, false)).name(faker.color().name()).build()
                ))
                .status(available)
                .build();
    }

    public static Pet update_Pet (Pet pet) {
        return PetMethod.updatePet(pet)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(Pet.class);
    }

    public static void delete_Pet(long petId) {
        PetMethod.deletePet(petId)
                .then()
                .assertThat()
                .statusCode(200);
    }
}

