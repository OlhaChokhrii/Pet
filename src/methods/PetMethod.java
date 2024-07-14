package methods;
import Enums.PetStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Pet;


public class PetMethod {

    public static Response addPet(Pet pet ) {
        return RestAssured .given()
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

    public static Response updatePet(String jsonBody) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .put("/pet");
    }

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
