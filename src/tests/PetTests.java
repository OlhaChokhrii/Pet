package tests;
import assertions.PetAssertions;
import models.Pet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import Enums.PetStatus;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import methods.PetMethod;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static steps.PetSteps.find_PetsByStatus;
import static steps.PetSteps.petDTO;

import org.junit.jupiter.api.BeforeAll;
import steps.PetSteps;

import java.util.List;

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

    @Test
    public void findPetsByStatusTest() {
        PetStatus status = PetStatus.available;
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
            /*PetAssertions.verifyPetName(response, updatedPetDTO);
            PetAssertions.verifyPetId(pet, updatedPetDTO.getId());*/
            PetAssertions.verifyPetWasCreated(response,updatedPetDTO);
        } finally {
            if (pet != null ) PetSteps.delete_Pet(pet.getId());
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

    @Test
    public void deletePetTest_Negative() {
        long nonExistentPetId = 123456789;
        Response response = PetMethod.deletePet(nonExistentPetId);
        PetAssertions.verifyPetNotFound(response, 404);
    }

}


