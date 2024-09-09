package methods;
import Enums.PetStatus;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Pet;


public class PetMethod {

    @Step("Add new pet")
    public static Response addPet(Pet pet ) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(pet)
                .when()
                .post("/pet");
    }

    public static Response findPetsByStatus(PetStatus status) {
        return RestAssured.given()
                .queryParam("status", status) //do not receive full response if not added to lower case;
                .when()
                .get("/pet/findByStatus");
    }

    @Step("Add new pet")
    public static Response updatePet(Pet pet) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(pet)
                .when()
                .put("/pet");
    }

    @Step("Delete pet with id - {0}")
    public static Response deletePet(long petId) {
        return RestAssured.given()
                .when()
                .delete("/pet/" + petId);
    }

    public static Response getPetById(long petId) {
        return RestAssured.given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");
    }
}
