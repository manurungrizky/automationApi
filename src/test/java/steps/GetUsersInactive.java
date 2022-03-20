package steps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dto.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

public class GetUsersInactive {
    private static final Logger logger = Logger.getLogger(steps.GetTodos.class.getName());
    Response response;
    RequestSpecification requestSpecification = RestAssured.given();
    String BASE_URL = "https://gorest.co.in/public/v2/users";


    @Test
    @Given("^I have hit API$")
    public void hitApi() {
        RestAssured.baseURI = BASE_URL;
        requestSpecification.header("Content-Type", "application/json");

        hitUrlProcess();

    }

    @Test
    @Then("^I want to check that user status is inactive$")
    public void validateData() {
        hitUrlProcess();
        User user = new User();
        List<String> resp = response.jsonPath().get();
        JsonArray jsonArray = new Gson().toJsonTree(resp).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.get(i)
                    .getAsJsonObject()
                    .get("status")
                    .getAsString()
                    .equals("inactive")) {
                user.setId(jsonArray.get(i).getAsJsonObject().get("id").getAsString());
                user.setName(jsonArray.get(i).getAsJsonObject().get("name").getAsString());
                user.setEmail(jsonArray.get(i).getAsJsonObject().get("email").getAsString());
                user.setGender(jsonArray.get(i).getAsJsonObject().get("gender").getAsString());
                user.setStatus(jsonArray.get(i).getAsJsonObject().get("status").getAsString());

                System.out.println(new Gson().toJson(user));
            }
        }

    }

     private String hitUrlProcess() {
        response = requestSpecification.get(BASE_URL);
        logger.info(":: RESPONSE :: " + response.getBody().asString());

        return response.getBody().asString();

    }
}
