package tests;
import assertions.PetAssertions;
import models.Pet;
import org.junit.jupiter.api.Test;
import Enums.PetStatus;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import methods.PetMethod;
import static steps.PetSteps.find_PetsByStatus;
import static steps.PetSteps.petDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import steps.PetSteps;
import java.util.List;
import java.util.stream.Stream;

public class PetTests {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); //
        RestAssured.baseURI = "https://petstore.swagger.io/v2";   //to not duplicate many times we can use such a global setting

    }

    @Test
    void post_AddNewPet_200() {
        Pet pet = null;
        try {
            Pet petDTO = petDTO();
            pet = PetSteps.add_Pet(petDTO);
            PetAssertions.verifyPetWasCreated(petDTO, pet);
        } finally {
            if (pet != null) PetSteps.delete_Pet(pet.getId());
        }
    }

    public static Stream<Arguments> negativeCasesCreatePet() {
        return Stream.of(
                Arguments.of("empty pet name", petDTO().setName(null)),
                Arguments.of("negative id", petDTO().setId(-1)),
                Arguments.of("empty pet category", petDTO().setCategory(null)),
                Arguments.of("empty pet status", petDTO().setStatus(null))
        );
    }
    @ParameterizedTest
    @MethodSource({"negativeCasesCreatePet"})
    public void post_AddNewPet_400(String testName, Pet petDTO) {
        Response response = PetMethod.addPet(petDTO);
        PetAssertions.verifyPetNotFound(response, 400);
    }

   @ParameterizedTest
   @EnumSource(value = PetStatus.class)
   void findPetsByStatus(PetStatus status) {
       List<Pet> pets = find_PetsByStatus(status);
       Pet firstPet = pets.get(0);
       System.out.println("First Pet ID: " + firstPet.getId());
       System.out.println("First Pet Name: " + firstPet.getName());
       System.out.println("First Pet Status: " + firstPet.getStatus());
       PetAssertions.verifyAllPetsByStatus(pets, status);
   }

    @Test
    public void updatePetTest() {
        Pet pet = null;
        try {
            Pet petDTO = PetSteps.petDTO();
            pet = PetSteps.add_Pet(petDTO);
            Pet updatedPetDTO = petDTO.setName("New name");
            Pet response = PetSteps.update_Pet(updatedPetDTO);
            PetAssertions.verifyPetWasCreated(response, updatedPetDTO);
        } finally {
            if (pet != null) PetSteps.delete_Pet(pet.getId());
        }
    }

    @Test //@Disabled
    public void testFindPetById() {
        Pet pet = null;
        try {
            Pet petDTO = petDTO();
            pet = PetSteps.add_Pet(petDTO);
            PetSteps.find_PetById(pet.getId());
            PetAssertions.verifyPetId(pet, petDTO.getId());
        } finally {
            if (pet != null) PetSteps.delete_Pet(pet.getId());
        }
    }

    @Test
    public void deletePetTest_Positive() {
        Pet pet = null;
        Boolean isDeleted = false;
        try {
            Pet petDTO = petDTO();
            pet = PetSteps.add_Pet(petDTO);
            PetSteps.delete_Pet(pet.getId());
            isDeleted = true;
        } finally {
            if (pet != null && !isDeleted) PetSteps.delete_Pet(pet.getId()); // знак оклику для позначення false
        }
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void deletePetTest_Negative(long id ) {
        Response response = PetMethod.deletePet(id);
        PetAssertions.verifyPetNotFound(response, 404);
    }
}

