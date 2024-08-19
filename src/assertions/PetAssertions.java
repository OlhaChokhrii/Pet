package assertions;
import Enums.PetStatus;
import io.restassured.response.Response;
import models.Pet;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetAssertions {

    public static void verifyPetWasCreated(Pet petDTO, Pet createdPet) {
        assertThat(petDTO, equalTo(createdPet));
    }

    public static void verifyAllPetsByStatus(List<Pet> pets, PetStatus status) {
        pets.forEach(pet ->
                assertThat(pet.getStatus(), equalTo(status))
                );
    }

    public static void verifyPetName(Response response, Pet updatedPetDTO) {
        response.then()
                .body("name", equalTo(updatedPetDTO.getName()));
        String actualName = response.jsonPath().getString("name");
        assertThat(actualName, equalTo(updatedPetDTO.getName()));
    }

    public static void verifyPetId(Pet pet, long expectedId) {
        assertThat(pet.getId(), equalTo(expectedId));
    }

    public static void verifyDeletePet(Response response, int expectedStatusCode) {
        assertThat(response.getStatusCode(), equalTo(expectedStatusCode));
      }
    }