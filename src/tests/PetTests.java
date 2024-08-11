package tests;
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
        } finally {
            if (pet != null) PetSteps.delete_Pet(pet.getId());
        }
        // public void addPetTest() {
        // Pet petToAdd = petDTO();
        //Pet response = PetSteps.add_Pet(petToAdd);
        // long expectedPetId = petToAdd.getId();
        // assertEquals(expectedPetId, response.getId(), "Expected pet ID to match");
        //PetStatus expectedStatus = petToAdd.getStatus();  // Convert PetStatus to String
        //PetStatus convertedStatus = response.getStatus();
        //assertEquals(expectedStatus, convertedStatus, "Expected pet status to match");

    }

    @Test
    public void findPetsByStatusTest() {
        PetStatus status = PetStatus.available;
        List<Pet> pets = find_PetsByStatus(status);
        Pet firstPet = pets.get(0);
        System.out.println("First Pet ID: " + firstPet.getId());
        System.out.println("First Pet Name: " + firstPet.getName());
        System.out.println("First Pet Status: " + firstPet.getStatus());
        assertEquals(status, firstPet.getStatus(), "Expected pet status to match");
    }

    @Test
    public void updatePetTest() {
        Pet pet = null;
        try {
            Pet petDTO = PetSteps.petDTO();
            pet = PetSteps.add_Pet(petDTO);
            Pet updatedPetDTO = petDTO.setName("New name");
            Response response = PetSteps.update_Pet(updatedPetDTO);
            response.then()
                    .statusCode(200)
                    .body("name", equalTo(updatedPetDTO.getName()))
                    .body("id", equalTo(updatedPetDTO.getId())); // Corrected the comparison with updatedPetDTO
            String actualName = response.jsonPath().getString("name");
            assertEquals(updatedPetDTO.getName(), actualName, "Expected updated pet name to match");
        } finally {
            if (pet != null ) PetSteps.delete_Pet(pet.getId());
            }

/*    public void updatePetTest() {
        Pet pet = null;
        try {
            Pet petDTO = PetSteps.petDTO();
            pet = PetSteps.add_Pet(petDTO);
            Pet updatedPetDTO = petDTO.setName("New name");
            Response response = PetSteps.update_Pet(updatedPetDTO);
            response.then()
                    .statusCode(200)
                    .body("name", equalTo(updatedPetDTO.getName()))
                    .body("id", equalTo((int) updatedPetDTO.getId()));
            String actualName = response.jsonPath().getString("name");
            assertEquals(updatedPetDTO.getName(), actualName, "Expected updated pet name to match");
        } finally {
            if (pet != null) PetSteps.delete_Pet(pet.getId());
        }*/
/*
        //public void updatePetTest() {
        // Pet petDTO = PetSteps.petDTO();
        //Pet updatedPetDTO = petDTO.setName("New name");
        //Response response = PetSteps.update_Pet(updatedPetDTO);
        //response.then()
        // .statusCode(200)
        // .body("name", equalTo(updatedPetDTO.getName()))
        //.body("id", equalToObject((int) petDTO.getId()));
        //String actualName = response.jsonPath().getString("name");
        //assertEquals(updatedPetDTO.getName(), actualName, "Expected updated pet name to match");
*/
        //long petId = 9223372036854601825L;
        //String updatedName = "Buddy";
        // String jsonBody = "{ \"id\": " + petId + ", \"name\": \"" + updatedName + "\", \"status\": \"available\" }";
        // Response response = PetMethod.updatePet(jsonBody);
        //assertEquals(200, response.getStatusCode(), "Expected status code 200");
        // response.then()
        // .statusCode(200)
        //.body("name",equalTo(updatedName))
        //.body("id",equalTo((long) petId));
        // String actualName = response.jsonPath().getString("name");
        // assertEquals(updatedName, actualName, "Expected updated pet name to match");
    }

    @Test //@Disabled
    public void testFindPetById() {
        Pet pet = null;
        try {
            Pet petDTO = petDTO();
            pet = PetSteps.add_Pet(petDTO);
            PetSteps.find_PetById(pet.getId());
        } finally {
            if (pet != null) PetSteps.delete_Pet(pet.getId());
        }

   /*     long petId = 847;
    //    Pet pet = PetSteps.find_PetById(petId);
        System.out.println("Pet ID: " + pet.getId());
        System.out.println("Pet Name: " + pet.getName());
        System.out.println("Pet Status: " + pet.getStatus());
        System.out.println("Pet Photo URLs: " + pet.getPhotoUrls());
*/    }

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
            if (pet != null && !isDeleted) PetSteps.delete_Pet(pet.getId()); // знак окліку для позначення false
        }
  /*    long petId = 9223372036854660000L;//9223372036854601825L;
        Response response = PetMethod.deletePet(petId);
        assertEquals(404, response.getStatusCode(), "Expected status code 200");
        response.then()
                .statusCode(404);
   */
    }

    @Test
    public void deletePetTest_Negative() {
        long nonExistentPetId = 123456789;
        Response response = PetMethod.deletePet(nonExistentPetId);
            assertEquals(404, response.getStatusCode(), "Expected status code 404");
            response.then().statusCode(404);
    }

}


