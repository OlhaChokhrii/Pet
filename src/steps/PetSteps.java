package steps;

import Enums.PetStatus;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Category;
import models.Pet;
import methods.PetMethod;
import models.Tag;
import java.util.Arrays;
import java.util.List;

import static Enums.PetStatus.*;
import static io.restassured.RestAssured.given;
import static methods.PetMethod.findPetsByStatus;

public class PetSteps {

    public static Pet find_PetById(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
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
        return given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .extract()
                .as(Pet.class);
    }
   public static Pet petDTO() {
        return Pet.builder()
               .id(98765)
               .name("Rex")
               .category(Category.builder().id(1).name("Dogs").build())
               .photoUrls(Arrays.asList("url1", "url2"))
               .tags(Arrays.asList(Tag.builder().id(1).name("tag1").build()))
               .status(available)
               .build();
    }

    public static Response update_Pet (Pet pet)  {
        return given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .put("https://petstore.swagger.io/v2/pet")
                .andReturn();
    //update_Pet(long petId, String jsonBody) {
        //PetMethod.updatePet(jsonBody)
                //.then()
               // .assertThat()
               // .statusCode(200);
    }

    public static void delete_Pet(long petId) {
        PetMethod.deletePet(petId)
                .then()
                .assertThat()
                .statusCode(200);

    }
}



